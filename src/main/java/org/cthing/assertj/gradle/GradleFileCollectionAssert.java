/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.file.FileCollection;


/**
 * AssertJ assertions for unit testing a Gradle {@link FileCollection}.
 */
@SuppressWarnings("UnusedReturnValue")
public class GradleFileCollectionAssert
        extends AbstractGradleFileCollectionAssert<GradleFileCollectionAssert, FileCollection> {

    /**
     * Constructs the assertion for the specified Gradle file collection.
     *
     * @param fileCollection Gradle file collection to test
     */
    public GradleFileCollectionAssert(final FileCollection fileCollection) {
        super(fileCollection);
    }

    /**
     * Creates the assertion for the specified Gradle file collection.
     *
     * @param fileCollection  Gradle file collection to test
     * @return This assertion
     */
    public static GradleFileCollectionAssert assertThat(final FileCollection fileCollection) {
        return new GradleFileCollectionAssert(fileCollection);
    }
}
