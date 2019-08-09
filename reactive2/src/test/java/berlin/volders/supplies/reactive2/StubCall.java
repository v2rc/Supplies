package berlin.volders.supplies.reactive2;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okio.Timeout;

class StubCall implements Call {

    private boolean isCanceled = false;
    private Callback responseCallback;

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Response execute() {
        return null;
    }

    @Override
    public void enqueue(Callback responseCallback) {
        this.responseCallback = responseCallback;
    }

    @Override
    public void cancel() {
        this.isCanceled = true;
    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return isCanceled;
    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @NonNull
    @Override
    public Call clone() {
        throw new IllegalStateException();
    }

    void doOnResponse(Response response) throws IOException {
        responseCallback.onResponse(this, response);
    }

    void doOnFailure() {
        responseCallback.onFailure(this, new IOException());
    }
}
