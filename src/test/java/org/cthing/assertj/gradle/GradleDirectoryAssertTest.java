/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


@SuppressWarnings("ResultOfMethodCallIgnored")
public class GradleDirectoryAssertTest {

    private DirectoryProperty directory;
    private File file1;
    private File file2;

    @BeforeEach
    public void setup() throws IOException {
        final Project project = ProjectBuilder.builder().build();
        final File testDir = new File(project.getProjectDir(), "test");
        testDir.mkdirs();

        this.file1 = new File(testDir, "file1.txt");
        Files.writeString(this.file1.toPath(), "Hello World");
        this.file2 = new File(testDir, "file2.txt");
        Files.writeString(this.file2.toPath(), "Goodbye World");

        this.directory = project.getObjects().directoryProperty().fileValue(testDir);
    }

    @Test
    public void testAsFile() {
        assertThat(this.directory).get(GradleAssertFactories.DIRECTORY).asFile().exists();
    }

    @Test
    public void testAsString() {
        assertThat(this.directory).get(GradleAssertFactories.DIRECTORY).asString().endsWith("test");
    }

    @Test
    public void testGetFiles() {
        assertThat(this.directory).get(GradleAssertFactories.DIRECTORY)
                                  .getFiles()
                                  .contains(this.file1)
                                  .contains(this.file2);
    }
}
