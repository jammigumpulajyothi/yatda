package com.hcl.yatda;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;


/**
 * Yet another To Do App
 * @author SupriyaBar
 *
 */
public class App {

	/**
	 * Allows creating a new task, modifying an existing task, viewing all the available tasks and deleting an existing task.
	 * @param args
	 */
	public static void main(String[] args) {
		Task task = new Task();
		List<Task> tasks = new ArrayList<Task>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date createdDate = new Date();
		String date = sdf.format(createdDate);
		task.setId(1);
		task.setSubject("Installing Maven");
		task.setCreationDate(date);
		task.setInCharge("IT-TEAM");
		task.setLastChanged(date);
		task.setStatus("new");
		tasks.add(task);
		System.out.println("Task created at " + task.getCreationDate());
		Vertx vertx = Vertx.vertx();
		HttpServer server = vertx.createHttpServer();
		Router router = Router.router(vertx);

		// add task
		router.post("/addTask").produces("application/json").handler(BodyHandler.create()).handler(routingContext -> {
			HttpServerResponse httpServerResponse = routingContext.response();
			try {
				final Task t = Json.decodeValue(routingContext.getBody(), Task.class);
				Date d=new Date();
	
				System.out.println("The Incharge is  "+t.getInCharge());
				
				t.setCreationDate(sdf.format(d));
				t.setLastChanged(sdf.format(d));
				tasks.add(t);
				httpServerResponse.end("Task added successfully");
			} catch (Exception e) {
				e.printStackTrace();
				httpServerResponse.end("Something went wrong");
			}
		});

		// View tasks
		router.get("/viewTasks").produces("application/json").handler(routingContext -> {
			HttpServerResponse httpServerResponse = routingContext.response();
			httpServerResponse.setChunked(true).end(Json.encodePrettily(tasks));
		});

		// edit tasks

		router.post("/editTask").produces("application/json").handler(BodyHandler.create()).handler(routingContext -> {
			HttpServerResponse httpServerResponse = routingContext.response();
			try {
				Task t = Json.decodeValue(routingContext.getBody(), Task.class);
				Task edited = tasks.stream().filter(requiredTask -> requiredTask.getId()==(t.getId()))
						.findFirst().get();
				edited.setInCharge(edited.getInCharge());
				edited.setLastChanged(sdf.format(new Date()));
				edited.setStatus(t.getStatus());
				edited.setSubject(t.getSubject());
				edited.setId(t.getId());
				httpServerResponse.end("Task modified successfully");
			} 
			catch(NoSuchElementException e) {
				e.printStackTrace();
				httpServerResponse.end("Mentioned Task is not available");
			}
			catch (Exception e) {
				e.printStackTrace();
				httpServerResponse.end("Something went wrong");
			}
		});

		// delete task
		router.post("/deleteTask/:id").produces("application/json").handler(routingContext -> {
			HttpServerResponse httpServerResponse = routingContext.response();
			try {
				int id = Integer.parseInt(routingContext.request().getParam("id"));
				Task edited = tasks.stream().filter(requiredTask -> requiredTask.getId()==(id))
						.findFirst().get();
				tasks.remove(edited);
				httpServerResponse.end("Task deleted successfully");
			}
			catch(NoSuchElementException e) {
				e.printStackTrace();
				httpServerResponse.end("Mentioned Task is not available");
			}
			catch (Exception e) {
				e.printStackTrace();
				httpServerResponse.end("Something went wrong");
			}
		});
		
	
		
		router.get("/getTask/:id").produces("application/json").handler(routingContext -> {
			HttpServerResponse httpServerResponse = routingContext.response();
			try {
				int id = Integer.parseInt(routingContext.request().getParam("id"));
				Task oneTask = tasks.stream().filter(requiredTask -> requiredTask.getId()==(id))
						.findFirst().get();
				httpServerResponse.end("One Task Get successfully");
				System.out.println(oneTask.getId());
				System.out.println(oneTask.getInCharge());
				System.out.println(oneTask.getLastChanged());
				System.out.println(oneTask.getStatus());
				System.out.println(oneTask.getSubject());
				System.out.println(oneTask.getCreationDate());
				
			}
			catch(NoSuchElementException e) {
				
				httpServerResponse.end("Mentioned Task is not available");
				System.out.println("Please Check User Id");
			}
			catch (Exception e) {
				
				httpServerResponse.end("Something went wrong");
			}
		});
		server.requestHandler(router::accept).listen(9091);
	}
}
		
		
	