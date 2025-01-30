/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import org.assertj.core.api.InstanceOfAssertFactory;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.file.Directory;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.RegularFile;


/**
 * Assertion factories for use with {@link GradleProviderAssert#get(InstanceOfAssertFactory)}.
 */
public final class GradleAssertFactories {

    /** Assertion factory for a {@link org.gradle.api.artifacts.Configuration}. */
    public static final InstanceOfAssertFactory<Configuration, GradleConfigurationAssert> CONFIGURATION
            = new InstanceOfAssertFactory<>(Configuration.class, GradleConfigurationAssert::assertThat);

    /** Assertion factory for a {@link Directory}. */
    public static final InstanceOfAssertFactory<Directory, GradleDirectoryAssert> DIRECTORY
            = new InstanceOfAssertFactory<>(Directory.class, GradleDirectoryAssert::assertThat);

    /** Assertion factory for a {@link FileCollection}. */
    public static final InstanceOfAssertFactory<FileCollection, GradleFileCollectionAssert> FILE_COLLECTION
            = new InstanceOfAssertFactory<>(FileCollection.class, GradleFileCollectionAssert::assertThat);

    /** Assertion factory for a {@link Project}. */
    public static final InstanceOfAssertFactory<Project, GradleProjectAssert> PROJECT
            = new InstanceOfAssertFactory<>(Project.class, GradleProjectAssert::assertThat);

    /** Assertion factory for a {@link RegularFile}. */
    public static final InstanceOfAssertFactory<RegularFile, GradleRegularFileAssert> REGULAR_FILE
            = new InstanceOfAssertFactory<>(RegularFile.class, GradleRegularFileAssert::assertThat);

    /** Assertion factory for a {@link Task}. */
    public static final InstanceOfAssertFactory<Task, GradleTaskAssert> TASK
            = new InstanceOfAssertFactory<>(Task.class, GradleTaskAssert::assertThat);

    private GradleAssertFactories() {
    }
}
