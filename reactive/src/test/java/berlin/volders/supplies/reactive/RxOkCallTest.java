package berlin.volders.supplies.reactive;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import rx.observers.AssertableSubscriber;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
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

        AssertableSubscriber<Response> subscriber
                = RxOkCall.from(call).test();
        call.doOnResponse(response);

        subscriber.assertNoErrors();
        subscriber.assertValue(response);
        subscriber.assertCompleted();
    }

    @Test
    public void from_failure() throws Exception {
        AssertableSubscriber<Response> subscriber
                = RxOkCall.from(call).test();
        call.doOnFailure();

        subscriber.assertNotCompleted();
        subscriber.assertError(IOException.class);
    }

    @Test
    public void from_unsubscribe() throws Exception {
        AssertableSubscriber<Response> subscriber
                = RxOkCall.from(call).test();
        subscriber.unsubscribe();

        assertThat(call.isCanceled(), is(true));
    }
}
