/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class GradleDirectoryPropertyAssertTest {

    private DirectoryProperty directory;

    @BeforeEach
    public void setup() {
        final Project project = ProjectBuilder.builder().build();

        final File testDir = new File(project.getProjectDir(), "test");
        testDir.mkdirs();

        this.directory = project.getObjects().directoryProperty().fileValue(testDir);
    }

    @Test
    public void testAsFile() {
        assertThat(this.directory).getFile().exists();
    }

    @Test
    public void testAsString() {
        assertThat(this.directory).getString().endsWith("test");
    }
}
