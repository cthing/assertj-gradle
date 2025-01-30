/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.gradle.api.file.RegularFile;


/**
 * AssertJ assertions for unit testing a Gradle {@link RegularFile}.
 */
public class GradleRegularFileAssert extends AbstractAssert<GradleRegularFileAssert, RegularFile> {

    /**
     * Constructs the assertion for the specified Gradle regular file.
     *
     * @param file  Gradle regular file to test
     */
    public GradleRegularFileAssert(final RegularFile file) {
        super(file, GradleRegularFileAssert.class);
    }

    /**
     * Creates the assertion for the specified Gradle regular file.
     *
     * @param file Gradle regular file to test
     * @return This assertion
     */
    public static GradleRegularFileAssert assertThat(final RegularFile file) {
        return new GradleRegularFileAssert(file);
    }

    /**
     * Provides assertions of the {@link RegularFile} as a {@link java.io.File}.
     *
     * @return File assertions on the RegularFile.
     */
    public AbstractFileAssert<?> asFile() {
        isNotNull();
        return Assertions.assertThat(this.actual.getAsFile());
    }

    /**
     * Provides assertions of the {@link RegularFile} path as a {@link String}.
     *
     * @return String assertions on the RegularFile path.
     */
    @Override
    @SuppressWarnings("MethodDoesntCallSuperMethod")
    public AbstractStringAssert<?> asString() {
        isNotNull();
        return Assertions.assertThat(this.actual.getAsFile().getPath());
    }
}
