package com.yiande.jxjxc.update;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;

import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.mylibrary.api.utils.StringUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by admin on 2017/2/28.
 */
public class UpAPP {
    public UpAPP(Context context) {
        this.context = context;
    }

    /***
     * 检测 APP 是否有新版本
     */
    public String AppURL = "";//升级地址
    Context context;
    boolean showDialog = true;
    public static boolean isDa = false;
    OnUpAppLisetener upAppLisetener;

    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }

    public void setUpAppLisetener(OnUpAppLisetener upAppLisetener) {
        this.upAppLisetener = upAppLisetener;
    }

    public void upAPP(final Handler handler) {
//        OkGo.<JsonBean<VersionBean>>get(URLS.baserURL + URLS.GetAndroidVersion)
//                .tag("UPApp")
//                .execute(new JsonCallbackNoDialog<JsonBean<VersionBean>>() {
//                             @Override
//                             public void onSuccess(com.lzy.okgo.model.Response<JsonBean<VersionBean>> response) {
//                                 super.onSuccess(response);
//                                 Message message = new Message();
//                                 if (response.body().data != null) {
//                                     try {
//                                         String[] old = SystemUtil.getVerName(context).split("[.]");
//                                         String[] news = response.body().data.getSet_AndroidVersion().split("[.]");
//                                         AppURL = response.body().data.getSet_AndroidUpdateUrl();
//                                         message.what = 1;
//                                         if (compareVersion(old, news) == 0 || compareVersion(old, news) == 1) {
//                                             isDa = true;
//                                             DialogUtils.upAppDialog(context, "", response.body().data.getSet_AndroidDescription(), new View
//                                                     .OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     insertDummyContactWrapper(AppURL);
//                                                 }
//                                             }, null, false);
//                                             message.what = 2;
//                                         } else if (compareVersion(old, news) == 2) {
//                                             isDa = false;
//                                             DialogUtils.upAppDialog(context, "", response.body().data.getSet_AndroidDescription(), new View
//                                                     .OnClickListener() {
//                                                 @Override
//                                                 public void onClick(View v) {
//                                                     insertDummyContactWrapper(AppURL);
//                                                 }
//                                             }, null, true);
//                                             message.what = 3;
//                                         }
//                                     } catch (Exception e) {
//                                     }
//                                 }
//                                 if (handler != null)
//                                     handler.sendMessage(message);
//                             }
//
//                             @Override
//                             public void onError(com.lzy.okgo.model.Response<JsonBean<VersionBean>> response) {
//                                 super.onError(response);
//
//                             }
//                         }
//                );

    }


    /***
     * 下载 新版本的APP
     */

    public static void downloadAPP(Context context, String url) {
//        final LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//        final View view = inflater.inflate(R.layout.dialog_download, null, false);
//        final BGAProgressBar pbProgres = view.findViewById(R.id.down_bar);
//
//        OkGo.<File>get(url)
//                .execute(new FileCallback("/sdcard/temp/", "shopoa2.apk") {
//                             AlertDialog alert;
//                             AlertDialog.Builder builder;
//
//                             @Override
//                             public void onStart(Request<File, ? extends Request> request) {
//                                 super.onStart(request);
//                                 //初始化Builder
//                                 builder = new AlertDialog.Builder(context, R.style.NoBackGroundDialog);
//                                 builder.setCancelable(false);
//                                 alert = builder.create();
//                                 alert.getWindow().setDimAmount((float) 0.5);//设置昏暗度为0
//                                 alert.show();
//                                 alert.getWindow().setContentView(view);
//                             }
//
//                             @Override
//                             public void onSuccess(com.lzy.okgo.model.Response<File> response) {
//                                 alert.dismiss();
//                                 File file=response.body();
//                                 if (file.exists()){
//                                     SystemUtil.installApkFile(context, response.body());
//                                 }
//
//
//
//                             }
//
//                             @Override
//                             public void downloadProgress(Progress progress) {
//                                 super.downloadProgress(progress);
//                                 pbProgres.setProgress((int) (progress.fraction * 100));
//                             }
//
//                             @Override
//                             public void onFinish() {
//                                 super.onFinish();
//                                 if (alert != null && alert.isShowing())
//                                     alert.dismiss();
//                             }
//
//                             @Override
//                             public void onError(com.lzy.okgo.model.Response<File> response) {
//                                 super.onError(response);
//                                 MyDialog myDialog = new MyDialog(context);
//                                 myDialog.setContent("由于网路不稳定，下载失败，是否重新下载");
//                                 if (isDa) {
//                                     myDialog.setNegativeButton("");
//                                 } else {
//                                     myDialog.setNegativeButton("取消");
//                                 }
//                                 myDialog.setPositiveButton("重新下载")
//                                         .setListener(new MyDialog.OnCloseListener() {
//                                             @Override
//                                             public void onClick(Dialog dialog, boolean confirm) {
//                                                 if (confirm) {
//                                                     if (alert != null && alert.isShowing())
//                                                         alert.dismiss();
//                                                     UpAPP.downloadAPP(context, url);
//                                                 } else {
//                                                     if (alert != null && alert.isShowing())
//                                                         alert.dismiss();
//                                                 }
//                                             }
//                                         }).show();
//                                 myDialog.setCancelables(isDa);
//                             }
//
//                         }
//                );
    }

    /**
     * 安装apk
     */
    public static void installApk(Context context, File apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(String.valueOf(apkPath)));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(context, "com.yiande.api2.FileProvider", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(String.valueOf(apkPath))),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        System.exit(0);
    }

    //动态获取SD卡的权限

    private void insertDummyContactWrapper(String URL) {
        if (Build.VERSION.SDK_INT >= 23) {
            final int call = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (call != PackageManager.PERMISSION_GRANTED) {
                //如没有 SD卡的权限   回调该方法
                upAppLisetener.onUpappLisetener();
            } else {
                downloadAPP(context, AppURL);
            }
        } else {
            downloadAPP(context, AppURL);
        }
    }

    public interface OnUpAppLisetener {
        public void onUpappLisetener();
    }

    private String getFileContent(Context context) {
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        ZipFile zipfile = null;
        String s = "";
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith("META-INF/no.txt")) { //xxx 表示要读取的文件名
                    //利用ZipInputStream读取文件
                    long size = entry.getSize();
                    if (size > 0) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(zipfile.getInputStream(entry)));
                        String line;
                        while ((line = br.readLine()) != null) {  //文件内容都在这里输出了，根据你的需要做改变
                            s += line;
                        }
                        br.close();

                    } else {
                    }
                    break;
                }
            }
            return s;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }


    public static int compareVersion(String[] oldVersion, String[] newVersion) {
        if (oldVersion == null || newVersion == null) {
            return -1;
        }
        int k = -1;
        int leng1 = oldVersion.length;
        int leng2 = newVersion.length;
        int leng = Math.min(leng1, leng2);//取最小长度值
        for (int i = 0; i < leng; i++) {
            int io = StringUtil.toInt(oldVersion[i]);
            int in = StringUtil.toInt(newVersion[i]);
            if (in > io) {
                return i;
            } else if (in < io) {
                return -1;
            }
        }
        return k;
    }

}
