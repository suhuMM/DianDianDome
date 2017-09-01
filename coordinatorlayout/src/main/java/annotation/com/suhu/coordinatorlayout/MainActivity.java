package annotation.com.suhu.coordinatorlayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;

import com.suhu.library.Annotation;
import com.suhu.library.BindView;
import com.suhu.library.IALayout;
import com.suhu.library.SetLayout;

import java.util.ArrayList;
import java.util.List;


@SetLayout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements IALayout {
    @BindView(R.id.table)
    private TabLayout tab;
    @BindView(R.id.view_pager)
    private MyViewPager pager;
    @BindView(R.id.nested)
    private NestedScrollView nested;

    private ViewPagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Annotation.init(this);

        addData();
    }

    private void addData() {
        List<String> list = new ArrayList<>();
        list.add("运动秀");
        list.add("朋友圈");
        list.add("我的");

        for (String s : list) {
            tab.addTab(tab.newTab().setText(s));
        }
        //tab.setTabMode(TabLayout.FIND_VIEWS_WITH_TEXT);



        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentA());
        fragments.add(new FragmentB());
        fragments.add(new FragmentC());

        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragments,list);
        pager.setAdapter(adapter);
        tab.setupWithViewPager(pager);



    }


}
