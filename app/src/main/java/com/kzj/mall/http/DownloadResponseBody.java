package com.kzj.mall.http;

import com.kzj.mall.event.DownloadProgressEvent;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by Chenwy on 2018/1/5 14:13
 */

public class DownloadResponseBody extends ResponseBody {
    private final int MAX = 100;

    private ResponseBody responseBody;
    private DownloadProgressEvent downloadProgressEvent;

    // BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
    private BufferedSource bufferedSource;

    public DownloadResponseBody(ResponseBody responseBody) {
        this.responseBody = responseBody;
        downloadProgressEvent = new DownloadProgressEvent();
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                if (bytesRead != -1) {
                    downloadProgressEvent.progress = (int) (totalBytesRead * MAX / responseBody.contentLength());
                    downloadProgressEvent.total = MAX;
                    EventBus.getDefault().post(downloadProgressEvent);
                }
                return bytesRead;
            }
        };

    }
}
