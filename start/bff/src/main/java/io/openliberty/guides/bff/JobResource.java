package io.openliberty.guides.bff;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.openliberty.guides.bff.client.JobClient;
import io.openliberty.guides.bff.model.JobListModel;
import io.openliberty.guides.bff.model.JobModel;
import io.openliberty.guides.bff.model.JobResultModel;
import io.openliberty.guides.bff.model.JobsModel;

@Path("/jobs")
public class JobResource {

    @Inject
    @RestClient
    private JobClient jobClient;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JobListModel getJobs() {
        JobsModel jobs = jobClient.getJobs();
        return new JobListModel(jobs.getResults());
    }

    @GET
    @Path("{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JobResultModel getJob(@PathParam("jobId") String jobId) {
        return jobClient.getJob(jobId);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public JobModel createJob() {
        return jobClient.createJob();
    }
}