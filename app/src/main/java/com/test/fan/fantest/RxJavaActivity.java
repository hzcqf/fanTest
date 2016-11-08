package com.test.fan.fantest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.test.fan.fantest.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RxJavaActivity extends Activity {
    private String tag = "tag";
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        observable.subscribe(observer);
        observable.subscribe(subscriber);

        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

        String[] names = {"111","222","333","444","555"};
        Observable.from(names)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String name) {
                        Log.d(tag, name);
                    }
                });

//        final int drawableRes = R.drawable.ic_launcher;
//        imageView = (ImageView) findViewById(R.id.ivView);
//
//        Observable.create(new Observable.OnSubscribe<Drawable>() {
//            @Override
//            public void call(Subscriber<? super Drawable> subscriber) {
//                Drawable drawable = null;
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//                    drawable = getTheme().getDrawable(drawableRes);
//                }
//                subscriber.onNext(drawable);
//                subscriber.onCompleted();
//            }
//        }).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onNext(Drawable drawable) {
//                imageView.setImageDrawable(drawable);
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Toast.makeText(TestActivity.this, "Error!", Toast.LENGTH_SHORT).show();
//            }
//        });


        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer number) {
                        Log.d(tag, "number:" + number);
                    }
                });

//        Observable.just("images/logo.png") // 输入类型 String
//                .map(new Func1<String, Bitmap>() {
//                    @Override
//                    public Bitmap call(String filePath) { // 参数类型 String
//                        return getBitmapFromPath(filePath); // 返回类型 Bitmap
//                    }
//                })
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) { // 参数类型 Bitmap
//                        showBitmap(bitmap);
//                    }
//                });


//        Student[] students = ...;
//        Subscriber<String> subscriber = new Subscriber<String>() {
//            @Override
//            public void onNext(String name) {
//                Log.d(tag, name);
//            }
//            ...
//        };
//        Observable.from(students)
//                .map(new Func1<Student, String>() {
//                    @Override
//                    public String call(Student student) {
//                        return student.getName();
//                    }
//                })
//                .subscribe(subscriber);


//        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.newThread())
//                .map(mapOperator) // 新线程，由 observeOn() 指定
//                .observeOn(Schedulers.io())
//                .map(mapOperator2) // IO 线程，由 observeOn() 指定
//                .observeOn(AndroidSchedulers.mainThread)
//                .subscribe(subscriber);  // Android 主线程，由 observeOn() 指定

    }

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });


    Observer<String> observer = new Observer<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            Log.d(tag, "Item: " + s);
        }

        @Override
        public void onCompleted() {
            Log.d(tag, "Completed!");
        }

        @Override
        public void onError(Throwable e) {
            Log.d(tag, "Error!");
        }
    };

    Action1<String> onNextAction = new Action1<String>() {
        // onNext()
        @Override
        public void call(String s) {
            Log.d(tag, s);
        }
    };

    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        // onError()
        @Override
        public void call(Throwable throwable) {
            // Error handling
        }
    };

    Action0 onCompletedAction = new Action0() {
        // onCompleted()
        @Override
        public void call() {
            Log.d(tag, "completed");
        }
    };


    Observable observable1 = Observable.just("Hello", "Hi", "Aloha");
    // 将会依次调用：
    // onNext("Hello");
    // onNext("Hi");
    // onNext("Aloha");
    // onCompleted();


    String[] words = {"Hello", "Hi", "Aloha"};
    Observable observable2 = Observable.from(words);
    // 将会依次调用：
    // onNext("Hello");
    // onNext("Hi");
    // onNext("Aloha");
    // onCompleted();

}
