package tqm.bianfeng.com.xinanproject.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.utils.ScreenUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;
import tqm.bianfeng.com.xinanproject.MainActivity;
import tqm.bianfeng.com.xinanproject.R;

/**
 * Created by johe on 2017/4/21.
 */

public class WelcomeActivity extends Activity {

    @BindView(R.id.startPage_img)
    ImageView startPageImg;
    @BindView(R.id.activity_time_tv)
    TextView activityTimeTv;
    @BindView(R.id.activity_start_page)
    RelativeLayout activityStartPage;

    private CompositeSubscription mCompositeSubscription;
    private static final int TIMETOCOUNT = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScreenUtils.hideStatusBar(this);
        setContentView(R.layout.activity_welcome);
        mCompositeSubscription = new CompositeSubscription();
        ButterKnife.bind(this);

        countToEnter();
    }

    /**
     * 倒计时
     */
    private void countToEnter() {
        Subscription subscriptionCount = Observable.interval(0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .limit(TIMETOCOUNT)
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        return TIMETOCOUNT - aLong;
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        WelcomeActivity.this.finish();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        //                        activityTimeTv.setText(aLong + "秒");
                    }
                });
        mCompositeSubscription.add(subscriptionCount);

    }
}
