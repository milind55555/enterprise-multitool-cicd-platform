package com.example.bamboo;

import com.atlassian.bamboo.specs.api.builders.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.api.builders.task.Task;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.ArtifactDefinition;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.ArtifactSubscription;
import com.atlassian.bamboo.specs.api.builders.plan.repository.VcsRepositoryIdentifier;
import com.atlassian.bamboo.specs.api.builders.repository.git.GitRepository;
import com.atlassian.bamboo.specs.api.builders.plan.repository.Repository;

import java.util.Arrays;

/**
 * Bamboo Specs illustration. Adjust imports/API versions to your installed Bamboo release,
 * create repository credentials in Bamboo, then validate in a non-production project.
 */
@BambooSpec
public class EnterpriseDemoPlan {
    public Plan plan() {
        Repository repo = new GitRepository()
            .name("enterprise-multitool-cicd-platform")
            .url("https://git.example.com/team/enterprise-multitool-cicd-platform.git");

        Task verify = new ScriptTask()
            .description("Build and test")
            .inlineBody("cd app && mvn -B -ntp clean verify");

        Task scan = new ScriptTask()
            .description("Trivy filesystem scan")
            .inlineBody("trivy fs --exit-code 1 --severity HIGH,CRITICAL --ignore-unfixed .");

        Job job = new Job("verify", "VERIFY")
            .tasks(verify, scan)
            .artifacts(new Artifact()
                .name("Spring Boot JAR")
                .copyPattern("app/target/enterprise-demo.jar")
                .location("."));

        Stage stage = new Stage("Build, Test and Scan")
            .jobs(job);

        return new Plan(new com.atlassian.bamboo.specs.api.builders.project.Project()
            .key("DEMO").name("Enterprise Demo"), "MULTI", "Multi-tool CI/CD demo")
            .stages(stage)
            .linkedRepositories(repo);
    }
}
