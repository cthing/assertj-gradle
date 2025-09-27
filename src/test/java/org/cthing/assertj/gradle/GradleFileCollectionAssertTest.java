/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.gradle.api.Project;
import org.gradle.api.file.FileCollection;
import org.gradle.api.provider.Provider;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


public class GradleFileCollectionAssertTest {

    private File file1;
    private File file2;
    private FileCollection multiple;
    private FileCollection single;
    private FileCollection empty;
    private Provider<@NonNull FileCollection> provider;

    @BeforeEach
    public void setup() throws IOException {
        final Project project = ProjectBuilder.builder().build();

        this.file1 = project.file("test1.txt");
        Files.writeString(this.file1.toPath(), "Hello World");

        this.file2 = project.file("test2.txt");
        Files.writeString(this.file2.toPath(), "Goodbye World");

        this.multiple = project.files(this.file1, this.file2);
        this.single = project.files(this.file2);
        this.empty = project.files();

        this.provider = project.provider(() -> this.multiple);
    }

    @Test
    public void testIsEmpty() {
        assertThat(this.empty).isEmpty();
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.multiple).isEmpty());
    }

    @Test
    public void testIsNotEmpty() {
        assertThat(this.multiple).isNotEmpty();
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.empty).isNotEmpty());
    }

    @Test
    public void testContains() {
        assertThat(this.multiple).contains(this.file1).contains(this.file2);
        assertThat(this.multiple.getAsFileTree()).contains(this.file1).contains(this.file2);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.empty).contains(this.file1));
    }

    @Test
    public void testHasSingle() {
        assertThat(this.single).hasSingleFile();
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.multiple).hasSingleFile());
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.empty).hasSingleFile());
    }

    @Test
    public void testInstanceFactory() {
        assertThat(this.provider).get(GradleAssertFactories.FILE_COLLECTION).contains(this.file1);
    }

    @Test
    public void testAsFiles() {
        assertThat(this.multiple).asFiles().containsExactlyInAnyOrder(this.file1, this.file2);
        assertThat(this.single).asFiles().contains(this.file2);
        assertThat(this.empty).asFiles().isEmpty();
    }

    @Test
    public void testAsFile() {
        assertThat(this.single).asFile().isEqualTo(this.file2);
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.multiple).asFile());
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(this.empty).asFile());
    }
}
