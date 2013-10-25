/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Nicolas Moser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.prodyna.pac.conference.ejb.beans.arquillian;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.container.spi.Container;
import org.jboss.arquillian.container.spi.ContainerRegistry;
import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.container.spi.client.deployment.DeploymentScenario;
import org.jboss.arquillian.container.spi.client.protocol.metadata.ProtocolMetaData;
import org.jboss.arquillian.container.spi.event.*;
import org.jboss.arquillian.container.spi.event.container.AfterStart;
import org.jboss.arquillian.container.spi.event.container.BeforeStop;
import org.jboss.arquillian.container.test.impl.client.deployment.event.GenerateDeployment;
import org.jboss.arquillian.core.api.Event;
import org.jboss.arquillian.core.api.Instance;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.api.event.ManagerStarted;
import org.jboss.arquillian.core.spi.EventContext;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.arquillian.test.spi.annotation.ClassScoped;
import org.jboss.arquillian.test.spi.annotation.TestScoped;
import org.jboss.arquillian.test.spi.context.ClassContext;
import org.jboss.arquillian.test.spi.event.suite.Before;
import org.jboss.arquillian.test.spi.event.suite.BeforeClass;

import java.util.concurrent.Callable;

/**
 * ArquillianSuiteExtension
 * <p/>
 * Author: Nicolas Moser
 * Date: 18.09.13
 * Time: 08:49
 */
public class ArquillianSuiteExtension implements LoadableExtension {

	public void register(ExtensionBuilder builder) {

		builder.observer(SuiteDeployer.class);
	}

	public static class SuiteDeployer {

		private Class<?> deploymentClass;

		private DeploymentScenario suiteDeploymentScenario;

		@Inject
		@ClassScoped
		private InstanceProducer<DeploymentScenario> classDeploymentScenario;

		@Inject
		private Event<DeploymentEvent> deploymentEvent;

		@Inject
		private Event<GenerateDeployment> generateDeploymentEvent;

		@Inject
		// Active some form of ClassContext around our deployments due to assumption bug in AS7 extension.
		private Instance<ClassContext> classContext;

		private ProtocolMetaData cachedProtocolMetaData;

		@TestScoped
		@Inject
		private InstanceProducer<ProtocolMetaData> testScopedProtocolMetaData;

		public void startup(@Observes(precedence = -100) ManagerStarted event, ArquillianDescriptor descriptor) {

			deploymentClass = getDeploymentClass(descriptor);

			executeInClassScope(new Callable<Void>() {
				public Void call() throws Exception {

					generateDeploymentEvent.fire(new GenerateDeployment(new TestClass(deploymentClass)));
					suiteDeploymentScenario = classDeploymentScenario.get();
					return null;
				}
			});
		}

		public void deploy(@Observes final AfterStart event, final ContainerRegistry registry) {

			executeInClassScope(new Callable<Void>() {
				public Void call() throws Exception {

					for (Deployment d : suiteDeploymentScenario.deployments()) {
						deploymentEvent.fire(
								new DeployDeployment(findContainer(registry, event.getDeployableContainer()), d));
					}
					return null;
				}
			});
		}

		public void undeploy(@Observes final BeforeStop event, final ContainerRegistry registry) {

			executeInClassScope(new Callable<Void>() {
				public Void call() throws Exception {

					for (Deployment d : suiteDeploymentScenario.deployments()) {
						deploymentEvent.fire(
								new UnDeployDeployment(findContainer(registry, event.getDeployableContainer()), d));
					}
					return null;
				}
			});
		}

		public void blockDeployManagedDeployments(@Observes EventContext<DeployManagedDeployments> ignored) {
			// We need to block DeployManagedDeployments event
		}

		public void storeProtocolMetaData(@Observes ProtocolMetaData protocolMetaData) {

			cachedProtocolMetaData = protocolMetaData;
		}

		public void resotreProtocolMetaData(@Observes EventContext<Before> eventContext) {

			testScopedProtocolMetaData.set(cachedProtocolMetaData);
			eventContext.proceed();
		}

		public void restoreDeploymentScenario(@Observes EventContext<BeforeClass> event) {
			// Setup the Suite level scenario as if it came from the TestClass
			event.proceed();
			classDeploymentScenario.set(suiteDeploymentScenario);
		}

		public void blockUnDeployManagedDeployments(@Observes EventContext<UnDeployManagedDeployments> ignored) {
			// We need to block UnDeployManagedDeployments event
		}

		private void executeInClassScope(Callable<Void> call) {

			try {
				classContext.get().activate(deploymentClass);
				call.call();
			} catch (Exception e) {
				throw new RuntimeException("Could not invoke operation", e);
			} finally {
				classContext.get().deactivate();
			}
		}

		private Container findContainer(ContainerRegistry registry, DeployableContainer<?> deployable) {

			for (Container container : registry.getContainers()) {
				if (container.getDeployableContainer() == deployable) {
					return container;
				}
			}
			return null;
		}

		private Class<?> getDeploymentClass(ArquillianDescriptor descriptor) {

			if (descriptor == null) {
				throw new IllegalArgumentException("Descriptor must be specified");
			}
			String className = descriptor.extension("suite").getExtensionProperties().get("deploymentClass");
			if (className == null) {
				throw new IllegalArgumentException(
						"A extension element with property deploymentClass must be specified in arquillian.xml");
			}
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("Could not load defined deploymentClass: " + className, e);
			}
		}
	}
}
