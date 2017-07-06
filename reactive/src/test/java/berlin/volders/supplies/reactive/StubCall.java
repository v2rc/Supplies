package berlin.volders.supplies.reactive;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

class StubCall implements Call {

    private boolean isCanceled = false;
    private Callback responseCallback;

    @Override
    public Request request() {
        return null;
    }

    @Override
    public Response execute() throws IOException {
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
    public Call clone() {
        return null;
    }

    void succees(Response response) throws IOException {
        responseCallback.onResponse(this, response);
    }

    void fail() {
        responseCallback.onFailure(this, new IOException());
    }
}
