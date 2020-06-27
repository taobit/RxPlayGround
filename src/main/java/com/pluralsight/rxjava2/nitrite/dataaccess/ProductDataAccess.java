package com.pluralsight.rxjava2.nitrite.dataaccess;

import com.pluralsight.rxjava2.nitrite.entity.Product;
import io.reactivex.rxjava3.core.Observable;
import org.dizitart.no2.Nitrite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductDataAccess {

    private final static Logger log = LoggerFactory.getLogger(ProductDataAccess.class);

    public static Observable<Product> select(Nitrite db) {
        return Observable.create(emitter -> {

            try {

                for (Product nextProduct : db.getRepository(Product.class)
                        .find()) {
                    //log.info(nextProduct.toString());
                    emitter.onNext(nextProduct);
                }
                emitter.onComplete();
            }
            catch( Throwable t ) {
                emitter.onError(t);
            }
        });
    }
}
