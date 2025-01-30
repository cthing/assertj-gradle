/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.Directory;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFile;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Provider;


/**
 * Provides access to all Gradle assertions.
 */
public final class GradleAssertions {

    private GradleAssertions() {
    }

    /**
     * Creates an assertion for the specified Gradle {@link Project}.
     *
     * @param project Gradle project to test
     * @return Project assertion
     */
    public static GradleProjectAssert assertThat(final Project project) {
        return GradleProjectAssert.assertThat(project);
    }

    /**
     * Creates an assertion for the specified Gradle {@link Provider}.
     *
     * @param <T> Type of the value contained in the {@link Provider}
     * @param provider Gradle provider to test
     * @return Provider assertion
     */
    public static <T> GradleProviderAssert<T> assertThat(final Provider<T> provider) {
        return GradleProviderAssert.assertThat(provider);
    }

    /**
     * Creates an assertion for the specified Gradle {@link FileCollection}.
     *
     * @param fileCollection Gradle file collection to test
     * @return File collection assertion
     */
    public static GradleFileCollectionAssert assertThat(final FileCollection fileCollection) {
        return GradleFileCollectionAssert.assertThat(fileCollection);
    }

    /**
     * Creates an assertion for the specified Gradle {@link RegularFileProperty}.
     *
     * @param fileProperty Gradle regular file property to test
     * @return Regular file property assertion
     */
    public static GradleRegularFilePropertyAssert assertThat(final RegularFileProperty fileProperty) {
        return GradleRegularFilePropertyAssert.assertThat(fileProperty);
    }

    /**
     * Creates an assertion for the specified Gradle {@link RegularFile}.
     *
     * @param file Gradle regular file to test
     * @return Regular file assertion
     */
    public static GradleRegularFileAssert assertThat(final RegularFile file) {
        return GradleRegularFileAssert.assertThat(file);
    }

    /**
     * Creates an assertion for the specified Gradle {@link DirectoryProperty}.
     *
     * @param dirProperty Gradle directory property to test
     * @return Directory property assertion
     */
    public static GradleDirectoryPropertyAssert assertThat(final DirectoryProperty dirProperty) {
        return GradleDirectoryPropertyAssert.assertThat(dirProperty);
    }

    /**
     * Creates an assertion for the specified Gradle {@link Directory}.
     *
     * @param dir Gradle directory to test
     * @return Directory assertion
     */
    public static GradleDirectoryAssert assertThat(final Directory dir) {
        return GradleDirectoryAssert.assertThat(dir);
    }

    /**
     * Creates an assertion for the specified Gradle {@link Configuration}.
     *
     * @param configuration Gradle configuration to test
     * @return Configuration assertion
     */
    public static GradleConfigurationAssert assertThat(final Configuration configuration) {
        return GradleConfigurationAssert.assertThat(configuration);
    }

    /**
     * Creates an assertion for the specified Gradle {@link Task}.
     *
     * @param task Gradle task to test
     * @return Task assertion
     */
    public static GradleTaskAssert assertThat(final Task task) {
        return GradleTaskAssert.assertThat(task);
    }
}
