package com.genialsir.projectplanner.okhttpprocess.request;

import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * @author genialsir@163.com (GenialSir) on 2017/9/29.
 */

public class OkCountRequestBody extends RequestBody{

    private RequestBody mDelegate;
    private DelegateListener mDelegateListener;

    public OkCountRequestBody(RequestBody delegate, DelegateListener delegateListener){
        mDelegate = delegate;
        mDelegateListener = delegateListener;
    }

    @Override
    public MediaType contentType() {
        return mDelegate.contentType();
    }

    @Override
    public long contentLength()  {
        try{
            return mDelegate.contentLength();
        }catch (IOException e){
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        mDelegate.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private final class CountingSink extends ForwardingSink{

        private long bytesWritten = 0;

        private CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            mDelegateListener.onRequestProgress(bytesWritten, contentLength());
        }
    }

    public interface DelegateListener{
        void onRequestProgress(long bytesWritten, long contentLength);
    }
}
