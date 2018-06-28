package com.liferay.knowledge.base.markdown.converter.internal.flexmark;

import java.io.IOException;

import com.liferay.knowledge.base.markdown.converter.MarkdownConverter;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;

/**
 *
 * @author Oliver Theobald
 *
 */
public class LiferayFlexmarkConverter implements MarkdownConverter {

	@Override
	public String convert(String markdown) throws IOException {

		DataHolder options = PegdownOptionsAdapter.flexmarkOptions(Extensions.NONE, CustomExtension.create());

		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();

		Node document = parser.parse(markdown);
		String html = renderer.render(document);

		return html;
	}

	static class CustomExtension implements HtmlRenderer.HtmlRendererExtension {

		@Override
		public void rendererOptions(final MutableDataHolder options) {

		}

		@Override
		public void extend(final HtmlRenderer.Builder rendererBuilder, final String rendererType) {
			rendererBuilder.nodeRendererFactory(new CustomLinkRenderer.Factory());
		}

		static CustomExtension create() {
			return new CustomExtension();
		}
	}

}
