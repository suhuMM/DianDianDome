package annotation.com.suhu.agentwebview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.suhu.library.Annotation;
import com.suhu.library.BindView;
import com.suhu.library.IALayout;
import com.suhu.library.SetLayout;

//http://www.ctolib.com/Justson-AgentWeb.html

@SetLayout(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements IALayout,ChromeClientCallbackManager.ReceivedTitleCallback{
    @BindView(R.id.rly)
    RelativeLayout relativeLayout;
    @BindView(R.id.titles)
    TextView textView;

    private AgentWeb mAgentWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().setTitle("网站");

        Annotation.init(this);
        mAgentWeb =  AgentWeb.with(this)
                .setAgentWebParent(relativeLayout,new RelativeLayout.LayoutParams(-1,-1))
                .useDefaultIndicator()
                .defaultProgressBarColor()
                .setReceivedTitleCallback(this)
                .createAgentWeb()
                .ready()
                .go("http://www.smartisan.com");

        mAgentWeb.getJsEntraceAccess().quickCallJs("callByAndroid");

    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        textView.setText(title);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
