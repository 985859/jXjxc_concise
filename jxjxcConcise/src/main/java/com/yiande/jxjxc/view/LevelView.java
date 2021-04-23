package com.yiande.jxjxc.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;
import com.mylibrary.api.utils.StringUtil;
import com.mylibrary.api.utils.ToastUtil;
import com.yiande.jxjxc.R;
import com.yiande.jxjxc.adapter.LevelAdapter;
import com.yiande.jxjxc.myInterface.LevelData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import me.simple.picker.PickerLayoutManager;
import me.simple.picker.PickerRecyclerView;

/**
 * @Description: 类作用描述
 * @Author: hukui
 * @Date: 2020/12/3 16:35
 */
public class LevelView<T extends LevelData> extends LinearLayout {
    TabLayout tabLayout;
    PickerRecyclerView recyclerView;
    TextView levelUp;
    TextView levelDown;
    private LevelAdapter<T> levelAdapter;
    private int maxLevel = 3;//默认 4 级  0 开始
    private int level = 0;// 当前层级
    private int selectIndex = 0;
    private Context context;
    private int titleColor;
    private int TitleSelectColor;
    private int indicatorHeight;
    private List<T> data;//数据源
    private List<Integer> selectPosition = new ArrayList();//选中的索引
    private List<String> selectID = new ArrayList();//选中的ID
    private List<String> selectName = new ArrayList();// 选中的 名称


    public LevelView(Context context) {
        this(context, null);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LevelView);
        titleColor = array.getColor(R.styleable.LevelView_level_TitleColor, context.getResources().getColor(R.color.textColor));
        TitleSelectColor = array.getColor(R.styleable.LevelView_level_TitleSelectColor, context.getResources().getColor(R.color.blue));
        indicatorHeight = (int) array.getDimension(R.styleable.LevelView_level_IndicatorHeight, 0);
        array.recycle();
        View view = LayoutInflater.from(context).inflate(R.layout.view_level, this);
        levelUp = view.findViewById(R.id.level_Up);
        levelDown = view.findViewById(R.id.level_Down);
        tabLayout = view.findViewById(R.id.levelTab);
        tabLayout.setTabTextColors(titleColor, TitleSelectColor);
        tabLayout.setSelectedTabIndicatorColor(TitleSelectColor);
        tabLayout.getTabSelectedIndicator().setBounds(0, 0, 0, indicatorHeight);
        tabLayout.addTab(tabLayout.newTab().setText("请选择"));
        recyclerView = view.findViewById(R.id.levelRec);
        PickerLayoutManager.Builder builder = new PickerLayoutManager.Builder();
        builder.setScaleY(0.95f);
        builder.setAlpha(0.5f);
        builder.setVisibleCount(5);
        PickerLayoutManager layoutManager = builder.build();
        recyclerView.setLayoutManager(layoutManager);
        levelAdapter = new LevelAdapter();
        recyclerView.setAdapter(levelAdapter);
        recyclerView.addOnItemFillListener(new PickerLayoutManager.OnItemFillListener() {
            @Override
            public void onItemSelected(@NotNull View itemView, int position) {
                selectIndex = position;
                TextView textView = itemView.findViewById(R.id.itmLevel_Text);
                if (textView != null) {
                    textView.setTextSize(18);
                    textView.setTextColor(context.getResources().getColor(R.color.textColor));
                }
            }

            @Override
            public void onItemUnSelected(@NotNull View itemView, int position) {
                TextView textView = itemView.findViewById(R.id.itmLevel_Text);
                if (textView != null) {
                    textView.setTextSize(14);
                    textView.setTextColor(context.getResources().getColor(R.color.gray4));
                }
            }
        });
        recyclerView.addOnSelectedItemListener(new Function1<Integer, Unit>() {
            @Override
            public Unit invoke(Integer integer) {
                setselectBean(levelAdapter.getData(), level, integer, false);
                return null;
            }
        });

        levelDown.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setNextLevelData(selectIndex);
            }
        });

        levelUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                setUpLevelData();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (level != tab.getPosition()) {
                    for (int i = level; i >= tab.getPosition(); i--) {
                        if (i == tab.getPosition()) {
                            break;
                        }
                        tabLayout.removeTabAt(i);
                        selectPosition.remove(i);
                        selectID.remove(i);
                        selectName.remove(i);
                    }
                    // 记录当前显示的层级
                    level = tab.getPosition();
                    levelAdapter.setList(getLevelData(level));
                    if (selectPosition.size() > level) {
                        recyclerView.smoothScrollToPosition(selectPosition.get(level));
                    }
                    showUpDown();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    private void showUpDown() {
        if (level == 0) {
            levelUp.setVisibility(INVISIBLE);
        } else {
            levelUp.setVisibility(VISIBLE);
        }
        if (level == maxLevel) {
            levelDown.setVisibility(INVISIBLE);
        } else {
            levelDown.setVisibility(VISIBLE);
        }
    }


    /**
     * @param position 当前层级 选中的 索引
     * @return
     * @author hukui
     * @time 2020/12/4
     * @Description 设置下一级选中的数据
     */
    private void setNextLevelData(int position) {
        List<T> levelData = levelAdapter.getItem(position).getChild();
        if (levelData != null && levelData.size() > 0) {
            level += 1;
            levelAdapter.setList(levelData);
            setselectBean(levelData, level, 0, true);
            recyclerView.smoothScrollToPosition(0);
        } else {
            ToastUtil.showShort( "当前层级没有下一级");
        }
        showUpDown();
    }

    /**
     * @return
     * @author hukui
     * @time 2020/12/4
     * @Description 设置上一级选中的数据
     */
    private void setUpLevelData() {
        if (level == 0) {
            levelUp.setVisibility(GONE);
            return;
        }
        List<T> levelData = getLevelData(level - 1);
        if (levelData != null && levelData.size() > 0) {
            if (tabLayout != null) {
                tabLayout.removeTabAt(level - 1);
            }
            selectPosition.remove(level);
            selectID.remove(level);
            selectName.remove(level);
            level = level - 1;
            levelAdapter.setList(levelData);
            if (level < selectPosition.size()) {
                recyclerView.smoothScrollToPosition(selectPosition.get(level));
            }

        }
        showUpDown();
    }

    /**
     * @param isReselect true 重新选择   false  记录上一次ID 定位到是一次的位置
     * @return
     * @author hukui
     * @time 2020/9/18
     * @Description
     */
    public void setData(List<T> data, boolean isReselect) {
        this.data = data;
        selectIndex = 0;
        levelAdapter.setList(data);
        if (isReselect) {
            setselectID(null);
        } else {
            List<String> ids = new ArrayList<>();
            ids.addAll(selectID);
            setselectID(ids);
        }
    }

    public void setData(List<T> data) {
        setData(data, true);
    }

    //根据ID 设置选中的内容
    public void setselectID(List<String> idList) {
        tabLayout.removeAllTabs();
        selectName.clear();
        selectPosition.clear();
        selectID.clear();
        level = 0;
        int index = 0;
        if (idList != null && idList.size() > 0) {
            List<T> levelDatas = data;
            if (levelDatas != null && levelDatas.size() > 0) {
                for (int i = 0; i < idList.size(); i++) {
                    level = i;
                    String id = idList.get(i) == null ? "" : idList.get(i);
                    for (int k = 0; k < levelDatas.size(); k++) {
                        T t = levelDatas.get(k);
                        if (id.equals(t.getID())) {
                            setselectBean(levelDatas, level, k, true);
                            if (t.getChild() != null && t.getChild().size() > 0) {
                                levelDatas = t.getChild();
                            } else {
                                index = k;
                            }
                            break;
                        }
                    }
                }
                levelAdapter.setList(getLevelData(level));
                if (index >= 0 && index < levelAdapter.getData().size()) {
                    recyclerView.smoothScrollToPosition(index);
                }

            }
        } else {
            showUpDown();
            levelAdapter.setList(data);
            recyclerView.smoothScrollToPosition(0);
            setselectBean(data, level, 0, true);
        }
    }


    /**
     * @param data     当前层级数据
     * @param level    当前层级
     * @param positoin 当前层级选中的索引
     * @param isAdd    当前层级添加还是修改
     * @return
     * @author hukui
     * @time 2020/12/4
     * @Description 设置当前层级选中的数据
     */
    private void setselectBean(List<T> data, int level, int positoin, boolean isAdd) {
        if (data == null || data.size() == 0) {
            return;
        }
        T bean = data.get(positoin);
        if (isAdd) {
            tabLayout.addTab(tabLayout.newTab().setText(bean.getName()), true);
        } else {
            if (level <= tabLayout.getTabCount()) {
                tabLayout.getTabAt(level).setText(bean.getName());
            }
        }
        addselectID(level, bean.getID());
        addselectName(level, bean.getName());
        addselectPositoin(level, positoin);
    }

    private void addselectPositoin(int level, int position) {
        if (selectPosition.size() > level) {
            //替换当前层级选中的结果
            selectPosition.set(level, position);
        } else {
            //添加当前层级选中的结果
            selectPosition.add(level, position);
        }
    }

    private void addselectID(int level, String id) {
        if (selectID.size() > level) {
            //替换当前层级选中的结果
            selectID.set(level, id);
        } else {
            //添加当前层级选中的结果
            selectID.add(level, id);
        }
    }

    private void addselectName(int level, String name) {
        if (selectName.size() > level) {
            //替换当前层级选中的结果
            selectName.set(level, name);
        } else {
            //添加当前层级选中的结果
            selectName.add(level, name);
        }
    }


    /**
     * @param level 要获取的层级
     * @return
     * @author hukui
     * @time 2020/12/4
     * @Description 获取  level 级的数据
     */
    private List<T> getLevelData(int level) {
        List<T> levelDatas = data;
        if (levelDatas != null && levelDatas.size() > 0 && level > 0) {
            for (int i = 0; i < level; i++) {
                if (selectPosition.get(i) != -1) {
                    levelDatas = levelDatas.get(selectPosition.get(i)).getChild();
                }
                if (levelDatas == null)
                    return levelDatas;
            }
        }
        return levelDatas;
    }


    public List<String> getselectNames() {
        return selectName;
    }

    public String getselectName(String divided) {
        return StringUtil.slipListToString(selectName, divided);
    }

    public List<String> getselectIds() {
        return selectID;
    }

    public String getselectID(String divided) {
        return StringUtil.slipListToString(selectID, divided);
    }


    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }


}