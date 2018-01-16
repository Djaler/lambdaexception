package com.github.djaler.lambdaexception;

import java.util.Comparator;
import java.util.function.*;

/**
 * @author Kirill Romanov
 */
@SuppressWarnings("RedundantThrows")
public final class LambdaExceptionUtil {
    @FunctionalInterface
    public interface ConsumerWithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumerWithExceptions<T, U, E extends Exception> {
        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface BiFunctionWithExceptions<T, U, R, E extends Exception> {
        R apply(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface FunctionWithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface SupplierWithExceptions<T, E extends Exception> {
        T get() throws E;
    }

    @FunctionalInterface
    public interface RunnableWithExceptions<E extends Exception> {
        void run() throws E;
    }

    @FunctionalInterface
    public interface PredicateWithException<T, E extends Exception> {
        boolean test(T t) throws E;
    }

    @FunctionalInterface
    public interface BiPredicateWithException<T, U, E extends Exception> {
        boolean test(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface ComparatorWithException<T, E extends Exception> {
        int compare(T t1, T t2) throws E;
    }


    public static <T, E extends Exception> Consumer<T> rethrowConsumer(ConsumerWithExceptions<T, E> consumer) throws E {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
            }
        };
    }

    public static <T, U, E extends Exception> BiConsumer<T, U> rethrowBiConsumer(BiConsumerWithExceptions<T, U, E> biConsumer) throws E {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
            }
        };
    }

    public static <T, U, R, E extends Exception> BiFunction<T, U, R> rethrowBiFunction(BiFunctionWithExceptions<T, U, R, E> biFunction) throws E {
        return (t, u) -> {
            try {
                return biFunction.apply(t, u);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return null;
            }
        };
    }

    public static <T, R, E extends Exception> Function<T, R> rethrowFunction(FunctionWithExceptions<T, R, E> function) throws E {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return null;
            }
        };
    }

    public static <T, E extends Exception> Supplier<T> rethrowSupplier(SupplierWithExceptions<T, E> supplier) throws E {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return null;
            }
        };
    }

    public static <E extends Exception> Runnable rethrowRunnable(RunnableWithExceptions<E> runnable) throws E {
        return () -> {
            try {
                runnable.run();
            } catch (Exception exception) {
                throwAsUnchecked(exception);
            }
        };
    }

    public static <T, E extends Exception> Predicate<T> rethrowPredicate(PredicateWithException<T, E> predicate) throws E {
        return t -> {
            try {
                return predicate.test(t);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return false;
            }
        };
    }

    public static <T, U, E extends Exception> BiPredicate<T, U> rethrowBiPredicate(BiPredicateWithException<T, U, E> biPredicate) throws E {
        return (t, u) -> {
            try {
                return biPredicate.test(t, u);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return false;
            }
        };
    }

    public static <T, E extends Exception> Comparator<T> rethrowComparator(ComparatorWithException<T, E> comparator) throws E {
        return (t1, t2) -> {
            try {
                return comparator.compare(t1, t2);
            } catch (Exception exception) {
                throwAsUnchecked(exception);
                return 0;
            }
        };
    }

    public static void uncheck(RunnableWithExceptions runnable) {
        try {
            runnable.run();
        } catch (Exception exception) {
            throwAsUnchecked(exception);
        }
    }

    public static <R, E extends Exception> R uncheck(SupplierWithExceptions<R, E> supplier) {
        try {
            return supplier.get();
        } catch (Exception exception) {
            throwAsUnchecked(exception);
            return null;
        }
    }

    public static <T, R, E extends Exception> R uncheck(FunctionWithExceptions<T, R, E> function, T t) {
        try {
            return function.apply(t);
        } catch (Exception exception) {
            throwAsUnchecked(exception);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception exception) throws E {
        throw (E) exception;
    }
}
