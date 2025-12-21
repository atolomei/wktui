package io.wktui.audio;

import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;

import wktui.base.BasePanel;

public class AudioPlayer extends BasePanel {

    private final String audioUrl;
    private final double startSeconds;

    
    public AudioPlayer(String id, String audioUrl) {
    	this(id, audioUrl,0);
    }
    /**
     * @param id Wicket id
     * @param audioUrl URL del audio
     * @param startSeconds posición inicial en segundos (0 para inicio)
     */
    public AudioPlayer(String id, String audioUrl, double startSeconds) {
        super(id);
        this.audioUrl = audioUrl;
        this.startSeconds = startSeconds;
        setOutputMarkupId(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        //super.renderHead(response);

        // Ejecutar JS cuando DOM y listeners estén listos
        String js = String.format(
            "requestAnimationFrame(() => setTimeout(() => window.initAudioPlayer('%s', %s), 100));",
            audioUrl, startSeconds
        );

        response.render(OnDomReadyHeaderItem.forScript(js));
    }
}
