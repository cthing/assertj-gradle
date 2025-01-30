/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.util.Collection;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.AbstractCollectionAssert;
import org.assertj.core.api.AbstractFileAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.ObjectAssert;
import org.gradle.api.file.FileCollection;


/**
 * Base class for all Gradle {@link FileCollection} based assertions.
 *
 * @param <SELF> Concrete assertion class
 * @param <T> Gradle file collection based type
 */
@SuppressWarnings("UnusedReturnValue")
public abstract class AbstractGradleFileCollectionAssert<SELF extends AbstractGradleFileCollectionAssert<SELF, T>,
        T extends FileCollection> extends AbstractAssert<SELF, T> {

    protected AbstractGradleFileCollectionAssert(final T fileCollection) {
        super(fileCollection, AbstractGradleFileCollectionAssert.class);
    }

    /**
     * Verifies that the file collection is empty.
     *
     * @return This assertion
     */
    public SELF isEmpty() {
        isNotNull();

        if (!this.actual.isEmpty()) {
            failWithMessage("Expected file collection to be empty");
        }

        return this.myself;
    }

    /**
     * Verifies that the file collection is not empty.
     *
     * @return This assertion
     */
    public SELF isNotEmpty() {
        isNotNull();

        if (this.actual.isEmpty()) {
            failWithMessage("Expected file collection to not be empty");
        }

        return this.myself;
    }

    /**
     * Verifies that the file collection contains the specified file.
     *
     * @param file File to verify is in the collection
     * @return This assertion
     */
    public SELF contains(final File file) {
        isNotNull();

        if (!this.actual.contains(file)) {
            failWithMessage("Expected file collection to contain file '%s', but does not", file);
        }

        return this.myself;
    }

    /**
     * Verifies that the file collection contains a single file.
     *
     * @return This assertion
     */
    public SELF hasSingleFile() {
        isNotNull();

        final int numFiles = this.actual.getFiles().size();
        if (numFiles == 0) {
            failWithMessage("Expected file collection to have a single file but is empty");
        }
        if (numFiles > 1) {
            failWithMessage("Expected file collection to have a single file, but has %d", numFiles);
        }

        return this.myself;
    }

    /**
     * Provides assertions of the file collection as a {@link java.util.Set} of {@link File}.
     *
     * @return Collection assertion on the files
     */
    public AbstractCollectionAssert<?, Collection<? extends File>, File, ObjectAssert<File>> asFiles() {
        isNotNull();
        return Assertions.assertThat(this.actual.getFiles());
    }

    /**
     * Verifies that the file collection contains a single file and provides a {@link File} assertion of the
     * file collection.
     *
     * @return File assertion
     */
    public AbstractFileAssert<?> asFile() {
        isNotNull();
        hasSingleFile();

        return Assertions.assertThat(this.actual.getSingleFile());
    }
}
