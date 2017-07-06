package berlin.volders.supplies.reactive2;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.observers.TestObserver;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RxOkCallTest {
    private StubCall call;

    @Before
    public void setup() {
        call = new StubCall();
    }

    @Test
    public void from_success() throws Exception {
        Request request = new Request.Builder()
                .url("http://some.fake.url")
                .build();
        Response response = new Response.Builder()
                .code(200)
                .request(request)
                .protocol(Protocol.HTTP_2)
                .message("")
                .build();

        TestObserver<Response> observer
                = RxOkCall.from(call).test();
        call.succees(response);

        observer.assertSubscribed();
        observer.assertNoErrors();
        observer.assertValue(response);
    }

    @Test
    public void from_failure() throws Exception {
        TestObserver<Response> observer
                = RxOkCall.from(call).test();
        call.fail();

        observer.assertSubscribed();
        observer.assertError(IOException.class);
    }

    @Test
    public void from_unsubscribe() throws Exception {
        TestObserver<Response> observer
                = RxOkCall.from(call).test();
        observer.dispose();

        observer.assertNotSubscribed();
        assertThat(observer.isDisposed(), is(true));
        assertThat(call.isCanceled(), is(true));
    }
}
