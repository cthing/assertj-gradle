/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.gradle.api.Transformer;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Provider;
import org.jspecify.annotations.Nullable;


/**
 * AssertJ assertions for unit testing a Gradle {@link DirectoryProperty}.
 */
public class GradleDirectoryPropertyAssert
        extends AbstractGradleProviderAssert<GradleDirectoryPropertyAssert, Directory, DirectoryProperty> {

    /**
     * Constructs the assertion for the specified Gradle directory property.
     *
     * @param directoryProperty Gradle directory property to test
     */
    public GradleDirectoryPropertyAssert(final DirectoryProperty directoryProperty) {
        super(directoryProperty);
    }

    /**
     * Creates the assertion for the specified Gradle directory property.
     *
     * @param directoryProperty Gradle directory property to test
     * @return This assertion
     */
    public static GradleDirectoryPropertyAssert assertThat(final DirectoryProperty directoryProperty) {
        return new GradleDirectoryPropertyAssert(directoryProperty);
    }

    /**
     * Verifies that the {@link DirectoryProperty} is not {@code null}, not empty and returns a
     * {@link GradleRegularFileAssert} assertion that allows chaining assertions on the directory.
     *
     * @return New {@link GradleDirectoryAssert} for assertions chaining on the directory.
     * @throws AssertionError if the {@link DirectoryProperty} is null or empty.
     */
    public GradleDirectoryAssert getDirectory() {
        return get(GradleAssertFactories.DIRECTORY);
    }

    /**
     * Verifies that the {@link DirectoryProperty} is not {@code null}, not empty and returns an
     * {@link AbstractFileAssert} assertion that allows chaining assertions on the directory as a
     * {@link java.io.File}.
     *
     * @return New {@link AbstractFileAssert} for assertions chaining on the contained directory as a
     *      {@link java.io.File}.
     * @throws AssertionError if the {@link DirectoryProperty} is null or empty.
     */
    public AbstractFileAssert<?> getFile() {
        return getDirectory().asFile();
    }

    /**
     * Verifies that the {@link DirectoryProperty} is not {@code null}, not empty and returns an
     * {@link AbstractStringAssert} assertion that allows chaining assertions on the directory path
     * as a {@link String}.
     *
     * @return New {@link AbstractStringAssert} for assertions chaining on the contained directory path
     *      as a {@link String}.
     * @throws AssertionError if the {@link DirectoryProperty} is null or empty.
     */
    public AbstractStringAssert<?> getString() {
        return getDirectory().asString();
    }

    /**
     * Calls {@link Provider#map(Transformer) map} on the {@code Provider} under test. Assertions can then be made
     * on the {@code Provider} resulting from the map call.
     *
     * @param <V> Type contained in the {@link Provider} following the {@link Provider#map(Transformer) map} call.
     * @param transformer {@link Transformer} to use in the {@link Provider#map(Transformer) map} call
     * @return New GradleProviderAssert for assertions on the map of the directory property.
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> map(final Transformer<? extends @Nullable V, ? super Directory> transformer) {
        isNotNull();
        return GradleProviderAssert.assertThat(this.actual.map(transformer));
    }

    /**
     * Calls {@link Provider#flatMap(Transformer) flatMap} on the {@code Provider} under test. Assertions can then
     * be made on the {@code Provider} resulting from the flatMap call.
     *
     * @param <V> Type contained in the {@link Provider} following the
     *      {@link Provider#flatMap(Transformer) flatMap} call
     * @param transformer {@link Transformer} to use in the {@link Provider#flatMap(Transformer) flatMap} call
     * @return New GradleProviderAssert for assertions on the flatMap of the directory property.
     * @throws AssertionError if the {@link Provider} is null.
     */
    public <V> GradleProviderAssert<V> flatMap(final Transformer<? extends @Nullable Provider<? extends V>,
            ? super Directory> transformer) {
        isNotNull();
        return GradleProviderAssert.assertThat(this.actual.flatMap(transformer));
    }
}
