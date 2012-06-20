/**
 * Code Sample for
 * Runtime Performance & Availability Monitoring for Java Systems.
 * IBM DevelperWorks.
 * Nicholas Whitehead (whitehead.nicholas@gmail.com)
 */

package org.runtimemonitoring.collectors.tracing.contextual;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import org.runtimemonitoring.tracing.ITracer;
import org.runtimemonitoring.tracing.TracerFactory;

/**
 * <p>
 * Title: PayrollProcessing
 * </p>
 * <p>
 * Description: Contextual and Threshold Tracing Example
 * </p>
 * 
 * @author whitehead.nicholas@gmail.com
 * @version $Revision$
 */

public class PayrollProcessing {
	protected ITracer tracer = TracerFactory.getInstance();
	protected Random random = new Random(System.nanoTime());

	public static void main(String[] args) {
		log("Contextual Tracing Example: Payroll Processing");
		PayrollProcessing pp = new PayrollProcessing();
		pp.runBatch();
	}

	/**
	 * Runs a contrived "Batch Process" of 1000 clients.
	 */
	protected void runBatch() {
		for (int i = 0; i < 1000; i++) {
			processPayrollContextual(i);
		}
	}

	/**
	 * The contrived payroll processing invocation with no contextual tracing.
	 * 
	 * @param clientId
	 *            An Id of a fictional contrived client.
	 */
	public void processPayroll(long clientId) {
		// Collection<Employee> employees = null;
		// // Acquire the collection of employees
		// //...
		// //...
		// // Process each employee
		// for(Employee emp: employees) {
		// processEmployee(emp.getEmployeeId(), clientId);
		// }
	}

	/**
	 * The contrived payroll processing invocation with contextual tracing.
	 * 
	 * @param clientId
	 *            An Id of a fictional contrived client.
	 */
	public void processPayrollContextual(long clientId) {
		Collection<Employee> employees = null;
		// Acquire the collection of employees
		employees = popEmployees();
		// Process each employee
		int empCount = 0;
		String rangeName = null;
		long start = System.currentTimeMillis();
		for (Employee emp : employees) {
			processEmployee(emp.getEmployeeId(), clientId);
			empCount++;
		}
		rangeName = tracer.lookupRange("Payroll Processing", empCount);
		long elapsed = System.currentTimeMillis() - start;
		tracer.trace(elapsed, "Payroll Processing", rangeName,
				"Elapsed Time (ms)");
		tracer.traceIncident("Payroll Processing", rangeName,
				"Payrolls Processed");
		log("Processed Client with " + empCount + " employees.");
	}

	/**
	 * A contrived payroll process executed on an employee that takes 10 ms. to
	 * run.
	 * 
	 * @param employeeId
	 *            An Id of a fictional contrived employee.
	 * @param clientId
	 *            An Id of a fictional contrived client.
	 */
	public void processEmployee(long employeeId, long clientId) {
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}
	}

	/**
	 * Creates a contrived collection of employees for which payrolls will be
	 * processed.
	 * 
	 * @return A collection of employees.
	 */
	public Collection<Employee> popEmployees() {
		Collection<Employee> emps = new ArrayList<Employee>();
		int count = random.nextInt(250);
		for (int i = 0; i < count; i++) {
			emps.add(new Employee(i));
		}
		return emps;
	}

	/**
	 * Low maintenance logger.
	 * 
	 * @param message
	 */
	public static void log(Object message) {
		System.out.println(message);
	}

}
