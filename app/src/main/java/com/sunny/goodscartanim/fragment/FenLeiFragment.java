package com.sunny.goodscartanim.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.sunny.goodscartanim.MainActivity;
import com.sunny.goodscartanim.R;
import com.sunny.goodscartanim.localbean.ClassifyLeft;
import com.sunny.goodscartanim.localbean.ClassifyRight;
import com.sunny.goodscartanim.localbean.GoodsInfo;
import com.sunny.goodscartanim.test.TestGoodsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类页面
 */
public class FenLeiFragment extends Fragment implements View.OnClickListener {

    private ExpandableListView elvLeft;


    public static FenLeiFragment newInstance(String param1) {
        FenLeiFragment fragment = new FenLeiFragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public FenLeiFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify, container, false);

        view.findViewById(R.id.search).setOnClickListener(this);
        elvLeft = (ExpandableListView) view.findViewById(R.id.elv_left);
        elvLeft.setOnGroupClickListener(new GroupClickListener());
        elvLeft.setOnChildClickListener(new ChildClickListener());
        initLeftData();
        return view;
    }





    private void initRightData(final int id) {
        toGoodsFragment(id, GsonUtils.toJson(TestGoodsData.getRightTest()));
    }

    public void toEmptyFragment() {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
//        WhiteFragment codeloginFragment = WhiteFragment.newInstance("空白页");
        Fragment codeloginFragment = new Fragment();
        transaction.replace(R.id.tb, codeloginFragment);
        transaction.commit();
    }

    private void toGoodsFragment(int id, String json) {
        FragmentManager fm = getChildFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        RightFragment codeloginFragment = RightFragment.newInstance(json, id);
        transaction.replace(R.id.tb, codeloginFragment);
        transaction.commit();
    }

    private void initLeftData() {
        elvLeft.setAdapter(new ExpAdapter(getActivity(), getTestLeft()));
        initRightData(0);

    }

    public List<ClassifyLeft> getTestLeft() {
        List<ClassifyLeft> classifyList = new ArrayList<>();
        classifyList.add(new ClassifyLeft("特价专区", null));
        ClassifyLeft[] classifyLefts = new ClassifyLeft[]{new ClassifyLeft("新鲜水果", null),
                new ClassifyLeft("新鲜/水果", null)};
        classifyList.add(new ClassifyLeft("胶原蛋白", classifyLefts));
        classifyList.add(new ClassifyLeft("水果蔬菜", classifyLefts));
        return classifyList;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search) {
            ToastUtils.showLong("跳转搜索");
//            startActivity(new Intent(getActivity(), SearchActivity.class));
        }
    }

    public void addGoodsAnim(View itemView, String imgUrl) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).addGoodsAnim(itemView, imgUrl);
        }
    }

    private class ExpAdapter extends BaseExpandableListAdapter implements ExpandableListAdapter {
        List<ClassifyLeft> classifyList;
        private int index = 0;
        private int indexChild = 0;
        private LayoutInflater inflater;

        Map<Integer, GroupViewHolder> group = new HashMap<>();

        public ExpAdapter(Activity activity, List<ClassifyLeft> classifyList) {
            this.classifyList = classifyList;
            inflater = LayoutInflater.from(activity);
        }

        @Override
        public int getGroupCount() {
            return classifyList.size();
        }

        @Override
        public int getChildrenCount(int i) {
            ClassifyLeft[] child = classifyList.get(i).child;
            return child == null ? 0 : child.length;
        }

        @Override
        public ClassifyLeft getGroup(int i) {
            return classifyList.get(i);
        }

        @Override
        public ClassifyLeft getChild(int i, int i1) {
            return classifyList.get(i).child[i1];
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


        public void setSel(int sel) {
            this.index = sel;
//            notifyDataSetChanged();
            setChildSel(0);
        }

        public void setChildSel(int childSel) {
            indexChild = childSel;
            notifyDataSetChanged();

        }

        @Override
        public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
            GroupViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.elv_group, null);
                holder = new GroupViewHolder();
                holder.view = view.findViewById(R.id.ll_view);
                holder.viewSel = view.findViewById(R.id.cv_view);
                holder.txt = view.findViewById(R.id.tv);
                holder.txtSel = view.findViewById(R.id.tv_sel);
                view.setTag(holder);
            } else {
                holder = (GroupViewHolder) view.getTag();
            }
            group.put(groupPosition, holder);
            holder.setData(getGroup(groupPosition));
            holder.setSel(index == groupPosition);
            return view;
        }

        @Override
        public View getChildView(int groupPosition, int i, boolean b, View view, ViewGroup viewGroup) {
            ChildViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.elv_child, null);
                holder = new ChildViewHolder();
                holder.view = view.findViewById(R.id.line);
                holder.txt = view.findViewById(R.id.tv);
                view.setTag(holder);
            } else {
                holder = (ChildViewHolder) view.getTag();
            }
            holder.setData(getChild(groupPosition, i).parent);
            if (group.containsKey(groupPosition)) {
                group.get(groupPosition).setUnSel();
            }
            boolean ischildSel = index == groupPosition ? (indexChild == i) : false;
            holder.setSel(ischildSel);
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }


        private class GroupViewHolder {
            private View viewSel;
            private View view;
            private TextView txtSel;
            private TextView txt;

            public void setSel(boolean sel) {
                viewSel.findViewById(R.id.v_green).setVisibility(View.VISIBLE);
                viewSel.setVisibility(sel ? View.VISIBLE : View.INVISIBLE);
                view.setVisibility(sel ? View.GONE : View.VISIBLE);
            }

            public void setUnSel() {
                viewSel.setVisibility(View.VISIBLE);
                view.setVisibility(View.GONE);
                viewSel.findViewById(R.id.v_green).setVisibility(View.INVISIBLE);
            }

            public void setData(ClassifyLeft data) {
                txtSel.setText(data.parent);
                txt.setText(data.parent);
            }


        }

        private class ChildViewHolder {
            private View view;
            private TextView txt;

            public void setData(String data) {
                txt.setText(data);
            }

            public void setSel(boolean sel) {
                view.setVisibility(sel ? View.VISIBLE : View.INVISIBLE);
                txt.setTextColor(sel ? Color.parseColor("#4DC32D") : Color.parseColor("#333333"));
            }
        }
    }

    private class ChildClickListener implements ExpandableListView.OnChildClickListener {
        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int i1, long l) {
            Object obj = elvLeft.getExpandableListAdapter().getChild(groupPosition, i1);
            if (obj instanceof ClassifyLeft) {
                initRightData(((ClassifyLeft) obj).id);
            }
            ExpAdapter adapter = (ExpAdapter) elvLeft.getExpandableListAdapter();
            adapter.setChildSel(i1);

            return false;
        }
    }

    private int sign = -1;

    private class GroupClickListener implements ExpandableListView.OnGroupClickListener {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v,
                                    int groupPosition, long id_) {
            if (sign == -1) {
                // 展开被选的group
                parent.expandGroup(groupPosition);
                // 设置被选中的group置于顶端
                sign = groupPosition;
            } else if (sign == groupPosition) {
                parent.collapseGroup(sign);
                sign = -1;
            } else {
                parent.collapseGroup(sign);
                // 展开被选的group
                parent.expandGroup(groupPosition);
                sign = groupPosition;
            }

            ExpAdapter adapter = (ExpAdapter) parent.getExpandableListAdapter();
            adapter.setSel(groupPosition);
            Object obj = adapter.getGroup(groupPosition);
            if (obj instanceof ClassifyLeft) {
                ClassifyLeft[] child = ((ClassifyLeft) obj).child;
                int count = child == null ? 0 : child.length;
                int id;
                if (count == 0) {
                    id = ((ClassifyLeft) obj).id;
                } else {
                    id = child[0].id;
                }
                initRightData(id);
            }
            return true;
        }
    }


}

