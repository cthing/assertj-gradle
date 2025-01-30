/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.Transformer;
import org.gradle.api.provider.Provider;
import org.jspecify.annotations.Nullable;


/**
 * AssertJ assertions for unit testing a Gradle {@link Provider}.
 *
 * @param <VALUE> Type of the value contained in the {@link Provider}.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleProviderAssert<VALUE>
        extends AbstractGradleProviderAssert<GradleProviderAssert<VALUE>, VALUE, Provider<VALUE>> {

    /**
     * Constructs the assertion for the specified Gradle provider.
     *
     * @param provider  Gradle provider to test
     */
    public GradleProviderAssert(final Provider<VALUE> provider) {
        super(provider);
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
     * Calls {@link Provider#map(Transformer) map} on the {@code Provider} under test. Assertions can then be made
     * on the {@code Provider} resulting from the map call.
     *
     * @param <V> Type contained in the {@link Provider} following the {@link Provider#map(Transformer) map} call.
     * @param transformer {@link Transformer} to use in the {@link Provider#map(Transformer) map} call
     * @return New GradleProviderAssert for assertions on the map of the Provider
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> map(final Transformer<? extends @Nullable V, ? super VALUE> transformer) {
        isNotNull();
        return assertThat(this.actual.map(transformer));
    }

    /**
     * Calls {@link Provider#flatMap(Transformer) flatMap} on the {@code Provider} under test. Assertions can then
     * be made on the {@code Provider} resulting from the flatMap call.
     *
     * @param <V> Type contained in the {@link Provider} following the
     *      {@link Provider#flatMap(Transformer) flatMap} call
     * @param transformer {@link Transformer} to use in the {@link Provider#flatMap(Transformer) flatMap} call
     * @return New GradleProviderAssert for assertions on the flatMap of the Provider.
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> flatMap(final Transformer<? extends @Nullable Provider<? extends V>,
            ? super VALUE> transformer) {
        isNotNull();
        return assertThat(this.actual.flatMap(transformer));
    }
}
