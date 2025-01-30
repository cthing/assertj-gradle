/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.gradle.api.file.Directory;


/**
 * AssertJ assertions for unit testing a Gradle {@link Directory}.
 */
public class GradleDirectoryAssert extends AbstractAssert<GradleDirectoryAssert, Directory> {

    /**
     * Constructs the assertion for the specified Gradle directory.
     *
     * @param dir  Gradle directory to test
     */
    public GradleDirectoryAssert(final Directory dir) {
        super(dir, GradleDirectoryAssert.class);
    }

    /**
     * Creates the assertion for the specified Gradle directory.
     *
     * @param dir Gradle directory to test
     * @return This assertion
     */
    public static GradleDirectoryAssert assertThat(final Directory dir) {
        return new GradleDirectoryAssert(dir);
    }

    /**
     * Provides assertions of the {@link Directory} as a {@link java.io.File}.
     *
     * @return File assertions on the directory.
     */
    public AbstractFileAssert<?> asFile() {
        isNotNull();
        return Assertions.assertThat(this.actual.getAsFile());
    }

    /**
     * Provides assertions of the {@link Directory} path as a {@link String}.
     *
     * @return String assertions on the directory path.
     */
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public AbstractStringAssert<?> asString() {
        isNotNull();
        return Assertions.assertThat(this.actual.getAsFile().getPath());
    }

    /**
     * Provides assertions of the files contained in the directory.
     *
     * @return File collection assertions on the directory contents
     */
    public GradleFileCollectionAssert getFiles() {
        isNotNull();
        return GradleFileCollectionAssert.assertThat(this.actual.getAsFileTree());
    }
}
