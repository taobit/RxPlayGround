package com.pluralsight.rxjava2.module5;

import com.pluralsight.rxjava2.utility.MutableReference;
import com.pluralsight.rxjava2.utility.ThreadKt;
import com.pluralsight.rxjava2.utility.events.EventBase;
import com.pluralsight.rxjava2.utility.events.NewCommentPostedEvent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Supplier;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CommentServiceEventObservable {

    private static final String[] authorEmails = new String[]{

            "mrresponsible@aracnid.net",
            "cheater@worldreverse.org",
            "hook@calzonecorner.net",
            "toughguy@unitard.com"
    };

    public static Observable<EventBase> commentServiceEventGenerator() {

        return Observable.generate(
                (@NonNull Supplier<MutableReference<Integer>>) MutableReference::new,
                (offset, eventBaseEmitter) -> {

                    // Make sure we haven't run off the end of our list.
                    if (offset.getValue() >= authorEmails.length) {

                        // We have sent all the messages...send the
                        // onComplete event.
                        eventBaseEmitter.onComplete();
                    } else {

                        int nextValue = (offset.getValue() + 1) % authorEmails.length;

                        // Send out the next message
                        eventBaseEmitter.onNext(new NewCommentPostedEvent(
                                authorEmails[offset.getValue()],
                                authorEmails[nextValue],
                                randomString()
                        ));
                    }

                    // Increment our offset counter
                    offset.setValue( offset.getValue() + 1 );

                    // Slow things down
                    ThreadKt.sleep(1500, TimeUnit.MILLISECONDS);
                });
        }

    private static final Random random = new Random();

    private static String randomString() {

        String letters = "abcdefghijklmnopqrstuvwxyz ";

        StringBuilder returnBuffer = new StringBuilder(64);

        for (int i = 0; i < 64; i++) {
            returnBuffer.append(letters.charAt(random.nextInt(letters.length())));
        }

        return returnBuffer.toString();
    }
}
