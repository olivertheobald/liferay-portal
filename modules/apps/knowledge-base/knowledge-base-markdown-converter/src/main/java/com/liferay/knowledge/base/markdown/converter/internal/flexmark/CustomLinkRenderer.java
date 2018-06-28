package com.liferay.knowledge.base.markdown.converter.internal.flexmark;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.DelegatingNodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.options.DataHolder;

/**
 * 
 * @author Oliver Theobald
 *
 */
public class CustomLinkRenderer implements NodeRenderer {

	private static final Pattern LIFERAY_LINK = Pattern.compile("\\[\\]\\(id=([^\\s]+?)\\)");

	public static class Factory implements DelegatingNodeRendererFactory {
		@Override
		public NodeRenderer create(final DataHolder options) {
			return new CustomLinkRenderer();
		}

		@Override
		public Set<Class<? extends NodeRendererFactory>> getDelegates() {
			return null;
		}
	};

	@Override
	public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
		HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
		set.add(new NodeRenderingHandler<Link>(Link.class, new CustomNodeRenderer<Link>() {
			@Override
			public void render(Link node, NodeRendererContext context, HtmlWriter html) {

				Matcher matcher = LIFERAY_LINK.matcher(node.getChars());
				
				if (matcher.find()) {
					String link = matcher.group(0);
					html.raw(link);
				} else {
					// otherwise pass it for default rendering
					context.delegateRender();
				}

			}
		}));

		return set;
	}

}