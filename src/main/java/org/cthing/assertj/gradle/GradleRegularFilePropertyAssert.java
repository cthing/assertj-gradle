/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.gradle.api.Transformer;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;
import org.jspecify.annotations.Nullable;


/**
 * AssertJ assertions for unit testing a Gradle {@link RegularFileProperty}.
 */
public class GradleRegularFilePropertyAssert
        extends AbstractGradleProviderAssert<GradleRegularFilePropertyAssert, RegularFile, RegularFileProperty> {

    /**
     * Constructs the assertion for the specified Gradle regular file property.
     *
     * @param regularFileProperty Gradle regular file property to test
     */
    public GradleRegularFilePropertyAssert(final RegularFileProperty regularFileProperty) {
        super(regularFileProperty);
    }

    /**
     * Creates the assertion for the specified Gradle regular file property.
     *
     * @param fileProperty Gradle regular file property to test
     * @return This assertion
     */
    public static GradleRegularFilePropertyAssert assertThat(final RegularFileProperty fileProperty) {
        return new GradleRegularFilePropertyAssert(fileProperty);
    }

    /**
     * Verifies that the {@link RegularFileProperty} is not {@code null}, not empty and returns a
     * {@link GradleRegularFileAssert} assertion that allows chaining assertions on the regular file.
     *
     * @return New {@link GradleRegularFileAssert} for assertions chaining on the regular file.
     * @throws AssertionError if the {@link RegularFileProperty} is null or empty.
     */
    public GradleRegularFileAssert getRegularFile() {
        return get(GradleAssertFactories.REGULAR_FILE);
    }

    /**
     * Verifies that the {@link RegularFileProperty} is not {@code null}, not empty and returns an
     * {@link AbstractFileAssert} assertion that allows chaining assertions on the regular file as a
     * {@link java.io.File}.
     *
     * @return New {@link AbstractFileAssert} for assertions chaining on the contained regular file as a
     *      {@link java.io.File}.
     * @throws AssertionError if the {@link RegularFileProperty} is null or empty.
     */
    public AbstractFileAssert<?> getFile() {
        return getRegularFile().asFile();
    }

    /**
     * Verifies that the {@link RegularFileProperty} is not {@code null}, not empty and returns an
     * {@link AbstractStringAssert} assertion that allows chaining assertions on the regular file path
     * as a {@link String}.
     *
     * @return New {@link AbstractStringAssert} for assertions chaining on the contained regular file path
     *      as a {@link String}.
     * @throws AssertionError if the {@link RegularFileProperty} is null or empty.
     */
    public AbstractStringAssert<?> getString() {
        return getRegularFile().asString();
    }

    /**
     * Calls {@link Provider#map(Transformer) map} on the {@code Provider} under test. Assertions can then be made
     * on the {@code Provider} resulting from the map call.
     *
     * @param <V> Type contained in the {@link Provider} following the {@link Provider#map(Transformer) map} call.
     * @param transformer {@link Transformer} to use in the {@link Provider#map(Transformer) map} call
     * @return New GradleProviderAssert for assertions on the map of the regular file property.
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> map(final Transformer<? extends @Nullable V, ? super RegularFile> transformer) {
        isNotNull();
        return GradleProviderAssert.assertThat(this.actual.map(transformer));
    }

    /**
     * Calls {@link Provider#flatMap(Transformer) flatMap} on the {@code RegularFileProperty} under test. Assertions
     * can then be made on the {@code Provider} resulting from the flatMap call.
     *
     * @param <V> Type contained in the {@link Provider} following the {@link Provider#flatMap(Transformer) flatMap}
     *      call
     * @param transformer {@link Transformer} to use in the {@link Provider#flatMap(Transformer) flatMap} call
     * @return New GradleProviderAssert for assertions on the flatMap of the regular file property.
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> flatMap(final Transformer<? extends @Nullable Provider<? extends V>,
            ? super RegularFile> transformer) {
        isNotNull();
        return GradleProviderAssert.assertThat(this.actual.flatMap(transformer));
    }
}
