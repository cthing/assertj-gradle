/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.util.function.Consumer;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractObjectAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.assertj.core.internal.Conditions;
import org.gradle.api.Transformer;
import org.gradle.api.provider.Provider;
import org.jspecify.annotations.Nullable;

import static org.assertj.core.util.Preconditions.checkArgument;


/**
 * Custom AssertJ assertions for unit testing a Gradle {@link Provider}.
 *
 * @param <VALUE> Type of the value contained in the {@link Provider}.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleProviderAssert<VALUE> extends AbstractAssert<GradleProviderAssert<VALUE>, Provider<VALUE>> {

    /**
     * Constructs the assertion for the specified Gradle provider.
     *
     * @param provider  Gradle provider to test
     */
    public GradleProviderAssert(final Provider<VALUE> provider) {
        super(provider, GradleProviderAssert.class);
    }

    /**
     * Creates the assertion for the specified Gradle provider.
     *
     * @param <T> Type of the value contained in the {@link Provider}
     * @param provider  Gradle provider to test
     * @return This assertion
     */
    public static <T> GradleProviderAssert<T> assertThat(final Provider<T> provider) {
        return new GradleProviderAssert<>(provider);
    }

    /**
     * Verifies that the {@link Provider} contains the given value (alias of {@link #hasValue(Object)}).
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> contains(final VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);

        if (!this.actual.isPresent()) {
            failWithMessage("Expecting Provider to contain '%s' but was empty", expectedValue.toString());
        }

        if (!expectedValue.equals(this.actual.get())) {
            failWithMessage("Expecting Provider to contain '%s' but was '%s'", expectedValue.toString(),
                            this.actual.get().toString());
        }

        return this;

    }

    /**
     * Verifies that the {@link Provider} contains a value that is an instance of the specified class.
     *
     * @param clazz Expected class of the value inside the {@link Provider}
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> containsInstanceOf(final Class<?> clazz) {
        assertValueIsPresent();

        if (!clazz.isInstance(this.actual.get())) {
            failWithMessage("Expecting '%s' to contain an instance of '%s' but contained an instance of '%s'",
                            this.actual.getClass().getSimpleName(), clazz.getName(),
                            this.actual.get().getClass().getName());
        }

        return this;
    }

    /**
     * Verifies that the {@link Provider} contains the same instance as the specified value.
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> containsSame(final VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);

        if (!this.actual.isPresent()) {
            failWithMessage("Expecting Provider to contain '%s' but was empty", expectedValue.toString());
        }

        if (this.actual.get() != expectedValue) {
            failWithMessage("Expecting Provider to contain value identical to '%s'", expectedValue.toString());
        }

        return this;
    }

    /**
     * Verifies that the {@link Provider} contains the specified value (alias of {@link #contains(Object)}).
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> hasValue(final VALUE expectedValue) {
        return contains(expectedValue);
    }

    /**
     * Verifies that the {@link Provider} contains a value and gives this value to the given
     * {@link Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     *
     * @param requirement Allows further assertions on the object contained inside the {@link Provider}
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> hasValueSatisfying(final Consumer<VALUE> requirement) {
        assertValueIsPresent();
        requirement.accept(this.actual.get());
        return this;
    }

    /**
     * Verifies that the {@link Provider} contains a value which satisfies the given {@link Condition}.
     *
     * @param condition Condition to satisfy
     * @return This assertion
     * @throws AssertionError if the {@link Provider} is null, empty, or the value in the provider does not satisfy
     *      the specified condition.
     * @throws NullPointerException if the given condition is {@code null}.
     */
    public GradleProviderAssert<VALUE> hasValueSatisfying(final Condition<? super VALUE> condition) {
        assertValueIsPresent();
        Conditions.instance().assertIs(this.info, this.actual.get(), condition);
        return this;
    }

    /**
     * Verifies that there is a value present in the {@link Provider}.
     *
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> isPresent() {
        assertValueIsPresent();
        return this;
    }

    /**
     * Verifies that the {@link Provider} does not contain any value.
     *
     * @return This assertion
     */
    public GradleProviderAssert<VALUE> isEmpty() {
        isNotNull();
        if (this.actual.isPresent()) {
            failWithMessage("Expecting Provider to be empty but contains '%s'", this.actual.toString());
        }
        return this;
    }

    /**
     * Verifies that the {@link Provider} is not {@code null}, not empty and returns an Object assertion
     * that allows chaining assertions on the contained value.
     *
     * @return New {@link AbstractObjectAssert} for assertions chaining on the value of the Provider.
     * @throws AssertionError if the actual {@link Provider} is null or empty.
     */
    public AbstractObjectAssert<?, VALUE> get() {
        isPresent();
        return Assertions.assertThat(this.actual.get());
    }

    /**
     * Calls {@link Provider#map(Transformer) map} on the {@code Provider} under test. Assertions can then be made
     * on the {@code Provider} resulting from the map call.
     *
     * @param <T> Type contained in the {@link Provider} following the {@link Provider#map(Transformer) map} call.
     * @param transformer {@link Transformer} to use in the {@link Provider#map(Transformer) map} call
     * @return New GradleProviderAssert for assertions on the map of the Provider
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <T> GradleProviderAssert<T> map(final Transformer<? extends @Nullable T, ? super VALUE> transformer) {
        isNotNull();
        return assertThat(this.actual.map(transformer));
    }

    /**
     * Calls {@link Provider#flatMap(Transformer) flatMap} on the {@code Provider} under test. Assertions can then
     * be made on the {@code Provider} resulting from the flatMap call.
     *
     * @param <T> Type contained in the {@link Provider} following the
     *      {@link Provider#flatMap(Transformer) flatMap} call
     * @param transformer {@link Transformer} to use in the {@link Provider#flatMap(Transformer) flatMap} call
     * @return New GradleProviderAssert for assertions on the flatMap of the Provider.
     * @throws AssertionError if the actual {@link Provider} is null.
     */
    public <T> GradleProviderAssert<T> flatMap(final Transformer<? extends @Nullable Provider<? extends T>,
            ? super VALUE> transformer) {
        isNotNull();
        return assertThat(this.actual.flatMap(transformer));
    }

    private void checkNotNull(@Nullable final Object expectedValue) {
        checkArgument(expectedValue != null, "The expected value must not be <null>.");
    }

    private void assertValueIsPresent() {
        isNotNull();
        if (!this.actual.isPresent()) {
            failWithMessage("Expecting '%s' to contain a value, but it was empty", this.actual.toString());
        }
    }
}
