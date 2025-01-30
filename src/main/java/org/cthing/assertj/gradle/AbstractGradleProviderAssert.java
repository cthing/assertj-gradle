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
import org.assertj.core.api.InstanceOfAssertFactory;
import org.assertj.core.internal.Conditions;
import org.gradle.api.provider.Property;
import org.gradle.api.provider.Provider;
import org.jspecify.annotations.Nullable;

import static org.assertj.core.util.Preconditions.checkArgument;


/**
 * Base class for Gradle {@link Provider} base assertions.
 *
 * @param <SELF> Type of concrete assertion
 * @param <VALUE> Type of the value contained in the {@link Provider}
 * @param <PROVIDER> Provider based type (e.g. {@link Provider}, {@link Property})
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractGradleProviderAssert<SELF extends AbstractGradleProviderAssert<SELF, VALUE,
        PROVIDER>, VALUE, PROVIDER extends Provider<VALUE>> extends AbstractAssert<SELF, PROVIDER> {

    /**
     * Constructs the assertion for the specified Gradle provider.
     *
     * @param provider  Gradle provider to test
     */
    protected AbstractGradleProviderAssert(final PROVIDER provider) {
        super(provider, AbstractGradleProviderAssert.class);
    }

    /**
     * Verifies that the {@link Provider} contains the given value (alias of {@link #hasValue(Object)}).
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public SELF contains(final VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);

        if (!this.actual.isPresent()) {
            failWithMessage("Expecting provider to contain '%s' but was empty", expectedValue.toString());
        }

        if (!expectedValue.equals(this.actual.get())) {
            failWithMessage("Expecting provider to contain '%s' but was '%s'", expectedValue.toString(),
                            this.actual.get().toString());
        }

        return this.myself;

    }

    /**
     * Verifies that the {@link Provider} contains a value that is an instance of the specified class.
     *
     * @param clazz Expected class of the value inside the {@link Provider}
     * @return This assertion
     */
    public SELF containsInstanceOf(final Class<?> clazz) {
        assertValueIsPresent();

        if (!clazz.isInstance(this.actual.get())) {
            failWithMessage("Expecting '%s' to contain an instance of '%s' but contained an instance of '%s'",
                            this.actual.getClass().getSimpleName(), clazz.getName(),
                            this.actual.get().getClass().getName());
        }

        return this.myself;
    }

    /**
     * Verifies that the {@link Provider} contains the same instance as the specified value.
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public SELF containsSame(final VALUE expectedValue) {
        isNotNull();
        checkNotNull(expectedValue);

        if (!this.actual.isPresent()) {
            failWithMessage("Expecting provider to contain '%s' but was empty", expectedValue.toString());
        }

        if (this.actual.get() != expectedValue) {
            failWithMessage("Expecting provider to contain value identical to '%s'", expectedValue.toString());
        }

        return this.myself;
    }

    /**
     * Verifies that the {@link Provider} contains the specified value (alias of {@link #contains(Object)}).
     *
     * @param expectedValue Expected value inside the {@link Provider}
     * @return This assertion
     */
    public SELF hasValue(final VALUE expectedValue) {
        return contains(expectedValue);
    }

    /**
     * Verifies that the {@link Provider} contains a value and provides this value to the given
     * {@link Consumer} for further assertions. Should be used as a way of deeper asserting on the
     * containing object, as further requirement(s) for the value.
     *
     * @param requirement Allows further assertions on the object contained inside the {@link Provider}
     * @return This assertion
     */
    public SELF hasValueSatisfying(final Consumer<VALUE> requirement) {
        assertValueIsPresent();
        requirement.accept(this.actual.get());
        return this.myself;
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
    public SELF hasValueSatisfying(final Condition<? super VALUE> condition) {
        assertValueIsPresent();
        Conditions.instance().assertIs(this.info, this.actual.get(), condition);
        return this.myself;
    }

    /**
     * Verifies that there is a value present in the {@link Provider}.
     *
     * @return This assertion
     */
    public SELF isPresent() {
        assertValueIsPresent();
        return this.myself;
    }

    /**
     * Verifies that the {@link Provider} does not contain any value.
     *
     * @return This assertion
     */
    public SELF isEmpty() {
        isNotNull();
        if (this.actual.isPresent()) {
            failWithMessage("Expecting provider to be empty but contains '%s'", this.actual.toString());
        }
        return this.myself;
    }

    /**
     * Verifies that the {@link Provider} is not {@code null}, not empty and returns an Object assertion
     * that allows chaining assertions on the contained value.
     *
     * @return New {@link AbstractObjectAssert} for assertions chaining on the value of the Provider.
     * @throws AssertionError if the {@link Provider} is null or empty.
     */
    public AbstractObjectAssert<?, VALUE> get() {
        isPresent();
        return Assertions.assertThat(this.actual.get());
    }

    /**
     * Verifies that the {@link Provider} is not {@code null}, not empty and returns a new assertion instance
     * to chain assertions on the contained value. The {@code assertFactory} parameter allows a
     * {@link InstanceOfAssertFactory} to be specified, which narrows the assertions type or the contained value.
     * Optionally, the {@link InstanceOfAssertFactory} can be wrapped with
     * {@link Assertions#as(InstanceOfAssertFactory)} to make the assertion more readable.
     *
     * @param <ASSERT> Type of the resulting {@code Assert}
     * @param assertFactory Factory which verifies the type and creates the new {@code Assert}
     * @return New assertion instance narrowed for asserting on the value of the Provider
     * @throws AssertionError if the {@link Provider} is null or empty
     */
    public <ASSERT extends AbstractAssert<?, ?>> ASSERT get(final InstanceOfAssertFactory<?, ASSERT> assertFactory) {
        isPresent();
        return Assertions.assertThat(this.actual.get()).asInstanceOf(assertFactory);
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
