/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.Project;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


public class GradleRegularFileAssertTest {

    private RegularFileProperty regularFile;

    @BeforeEach
    public void setup() throws IOException {
        final Project project = ProjectBuilder.builder().build();

        final File file = project.file("test.txt");
        Files.writeString(file.toPath(), "Hello World");
        this.regularFile = project.getObjects().fileProperty().fileValue(file);
    }

    @Test
    public void testAsFile() {
        assertThat(this.regularFile).get(GradleAssertFactories.REGULAR_FILE).asFile().exists();
    }

    @Test
    public void testAsString() {
        assertThat(this.regularFile).get(GradleAssertFactories.REGULAR_FILE).asString().endsWith("test.txt");
    }
}
