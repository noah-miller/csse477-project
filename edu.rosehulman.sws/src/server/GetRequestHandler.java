/*
 * GetRequestHandler.java
 * Oct 17, 2015
 *
 * Simple Web Server (SWS) for EE407/507 and CS455/555
 * 
 * Copyright (C) 2011 Chandan Raj Rupakheti, Clarkson University
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License 
 * as published by the Free Software Foundation, either 
 * version 3 of the License, or any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/lgpl.html>.
 * 
 * Contact Us:
 * Chandan Raj Rupakheti (rupakhcr@clarkson.edu)
 * Department of Electrical and Computer Engineering
 * Clarkson University
 * Potsdam
 * NY 13699-5722
 * http://clarkson.edu/~rupakhcr
 */

package server;

import java.io.File;

import protocol.HttpRequest;
import protocol.HttpResponse;
import protocol.HttpResponseFactory;
import protocol.Protocol;

/**
 * 
 * @author Chandan R. Rupakheti (rupakhcr@clarkson.edu)
 */
public class GetRequestHandler implements IRequestHandler {

	@Override
	public HttpResponse handleRequest(HttpRequest request, String rootDirectory) {

		String uri = request.getUri();

		File file = new File(rootDirectory + uri);

		if (file.exists()) {
			if (file.isDirectory()) {
				// Look for default index.html file in a directory
				String location = rootDirectory + uri
						+ System.getProperty("file.separator")
						+ Protocol.DEFAULT_FILE;
				file = new File(location);
				if (file.exists()) {
					// Lets create 200 OK response
					return HttpResponseFactory
							.create200OK(file, Protocol.CLOSE);
				} else {
					// File does not exist so lets create 404 file not found
					// code
					return HttpResponseFactory
							.create404NotFound(Protocol.CLOSE);
				}
			} else {
				return HttpResponseFactory.create200OK(file, Protocol.CLOSE);
			}
		} else {
			return HttpResponseFactory.create404NotFound(Protocol.CLOSE);
		}
	}
}