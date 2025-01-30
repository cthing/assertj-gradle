/*
 * Copyright 2025 C Thing Software
 * SPDX-License-Identifier: Apache-2.0
 */

package org.cthing.assertj.gradle;

import java.io.File;

import org.gradle.api.DefaultTask;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.OutputFile;
import org.gradle.testfixtures.ProjectBuilder;
import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.cthing.assertj.gradle.GradleAssertions.assertThat;


public class GradleTaskAssertTest {

    public static class TestTask extends DefaultTask {

        @Nullable
        private String prop;
        @Nullable
        private File prop2;
        @Nullable
        private File prop3;

        public TestTask() {
            setDescription("Hello world");
            setGroup("Testing");
        }

        @Nullable
        public String getProp() {
            return this.prop;
        }

        public void setProp(final String prop) {
            this.prop = prop;
        }

        @OutputFile
        @Nullable
        public File getProp2() {
            return this.prop2;
        }

        public void setProp2(final File prop2) {
            this.prop2 = prop2;
        }

        @InputFile
        @Nullable
        public File getProp3() {
            return this.prop3;
        }

        public void setProp3(final File prop3) {
            this.prop3 = prop3;
        }
    }

    private Project project;
    private Provider<TestTask> testTaskProvider1;
    private Provider<TestTask> testTaskProvider2;
    private Provider<TestTask> testTaskProvider3;
    private Provider<TestTask> testTaskProvider4;
    private TestTask testTask1;

    @BeforeEach
    public void setup() {
        this.project = ProjectBuilder.builder().build();
        this.testTaskProvider2 = this.project.getTasks().register("testTask2", TestTask.class);
        this.testTaskProvider3 = this.project.getTasks().register("testTask3", TestTask.class);
        this.testTaskProvider4 = this.project.getTasks().register("testTask4", TestTask.class);
        this.testTaskProvider1 = this.project.getTasks().register("testTask1", TestTask.class,
                                                                  task -> task.dependsOn(this.testTaskProvider2,
                                                                                         this.testTaskProvider3));
        this.testTask1 = this.testTaskProvider1.get();
    }

    @Test
    public void testHasName() {
        assertThat(this.testTask1).hasName("testTask1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasName("foo"))
                .withMessage("Expected task to have name 'foo', but it was 'testTask1'");
    }

    @Test
    public void testInstanceFactory() {
        assertThat(this.testTaskProvider1).get(GradleAssertFactories.TASK).hasName("testTask1");
    }

    @Test
    public void testHasDescription() {
        assertThat(this.testTask1).hasDescription("Hello world");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasDescription("foo"))
                .withMessage("Expected task 'testTask1' to have description 'foo', but it was 'Hello world'");
    }

    @Test
    public void testHasGroup() {
        assertThat(this.testTask1).hasGroup("Testing");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasGroup("foo"))
                .withMessage("Expected task 'testTask1' to belong to group 'foo', but was 'Testing'");
    }

    @Test
    public void testHasPath() {
        assertThat(this.testTask1).hasPath(":testTask1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasPath(":foo"))
                .withMessage("Expected task 'testTask1' to have path ':foo', but was ':testTask1'");
    }

    @Test
    public void testEnabled() {
        this.testTask1.setEnabled(false);
        assertThat(this.testTask1).isDisabled();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).isEnabled())
                .withMessage("Expected task 'testTask1' to be enabled, but it was disabled");

        this.testTask1.setEnabled(true);
        assertThat(this.testTask1).isEnabled();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).isDisabled())
                .withMessage("Expected task 'testTask1' to be disabled, but it was enabled");
    }

    @Test
    public void testHasProperty() {
        assertThat(this.testTask1).hasProperty("prop");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasProperty("foo"))
                .withMessage("Expected task 'testTask1' to have property 'foo', but it does not");

        this.testTask1.setProp("value1");
        assertThat(this.testTask1).hasPropertyValue("prop", "value1");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).hasPropertyValue("prop", "value2"))
                .withMessage("Expected task 'testTask1' to have property 'prop' with value 'value2', but is does not");
    }

    @Test
    public void testDependsOn() {
        assertThat(this.testTask1).dependsOn(this.testTaskProvider2, this.testTaskProvider3);
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(this.testTask1).dependsOn(this.testTaskProvider4))
                .withMessage("Expected task 'testTask1' to depend on 'provider(task 'testTask4', "
                                     + "class org.cthing.assertj.gradle.GradleTaskAssertTest$TestTask)', "
                                     + "but it does not");
    }

    @Test
    public void testInputs() {
        assertThat(this.testTask1).hasInputs();
        assertThat(this.testTask1).getInputFiles().isEmpty();

        final Task task = this.project.getTasks().register("testTask17", DefaultTask.class).get();
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(task).hasInputs());
        assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> assertThat(task).getInputFiles().isEmpty());
    }

    @Test
    public void testOutputs() {
        assertThat(this.testTask1).hasOutputs();
        assertThat(this.testTask1).getOutputFiles().isEmpty();

        final Task task = this.project.getTasks().register("testTask17", DefaultTask.class).get();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(task).hasOutputs())
                .withMessage("Expected task 'testTask17' to have outputs, but it does not");
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(() -> assertThat(task).getOutputFiles().isEmpty())
                .withMessage("Expected task 'testTask17' to have outputs, but it does not");
    }
}
