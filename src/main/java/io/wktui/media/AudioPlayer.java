package io.wktui.media;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.request.resource.UrlResourceReference;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.Url;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

/**
 * A simple, elegant audio player Wicket component.
 * Usage:
 * - new AudioPlayer(id, "https://.../file.mp3")
 * - new AudioPlayer(id, resourceReference)
 */
public class AudioPlayer extends Panel {

    private static final long serialVersionUID = 1L;

    private final ResourceReference resourceRef;
    private final String srcUrl;

    // configuration
    private boolean includeDownloadMenu = true;
    private double defaultPlaybackRate = 1.0;
    private double defaultVolume = 0.5;

    public AudioPlayer(String id, String srcUrl) {
        super(id);
        this.resourceRef = null;
        this.srcUrl = srcUrl;
        setOutputMarkupId(true);
    }

    public AudioPlayer(String id, UrlResourceReference resourceReference) {
        super(id);
        this.resourceRef = resourceReference;
        this.srcUrl = null;
        setOutputMarkupId(true);
    }

    public void setIncludeDownloadMenu(boolean include) {
        this.includeDownloadMenu = include;
    }

    public void setDefaultPlaybackRate(double r) {
        this.defaultPlaybackRate = r;
    }

    public void setDefaultVolume(double v) {
        this.defaultVolume = v;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        String finalSrc = this.srcUrl;
        if (finalSrc == null && resourceRef != null) {
            // build URL for the resource reference
            finalSrc = RequestCycle.get().urlFor(resourceRef, null).toString();
        }

        WebMarkupContainer container = new WebMarkupContainer("container");
        container.setOutputMarkupId(true);
        add(container);

        // left controls
        WebMarkupContainer left = new WebMarkupContainer("left");
        left.setOutputMarkupId(true);
        container.add(left);

        WebMarkupContainer btnReset = new WebMarkupContainer("reset");
        btnReset.setOutputMarkupId(true);
        left.add(btnReset);

        WebMarkupContainer btnPlay = new WebMarkupContainer("play");
        btnPlay.setOutputMarkupId(true);
        left.add(btnPlay);

        WebMarkupContainer btnEnd = new WebMarkupContainer("end");
        btnEnd.setOutputMarkupId(true);
        left.add(btnEnd);


        // time label
        Label time = new Label("time", Model.of("00:00 / 00:00"));
        time.setOutputMarkupId(true);
        left.add(time);

        // center progress
        WebMarkupContainer center = new WebMarkupContainer("center");
        center.setOutputMarkupId(true);
        container.add(center);

        WebMarkupContainer progress = new WebMarkupContainer("progress");
        progress.setOutputMarkupId(true);
        center.add(progress);

        // right controls
        WebMarkupContainer right = new WebMarkupContainer("right");
        right.setOutputMarkupId(true);
        container.add(right);

        // speed select
        WebMarkupContainer speed = new WebMarkupContainer("speed");
        speed.setOutputMarkupId(true);
        right.add(speed);

        WebMarkupContainer volume = new WebMarkupContainer("volume");
        volume.setOutputMarkupId(true);
        right.add(volume);

        WebMarkupContainer downloadMenu = new WebMarkupContainer("downloadMenu");
        downloadMenu.setOutputMarkupId(true);
        downloadMenu.setVisible(this.includeDownloadMenu);
        right.add(downloadMenu);

        WebMarkupContainer downloadLink = new WebMarkupContainer("downloadLink");
        downloadLink.setOutputMarkupId(true);
        downloadMenu.add(downloadLink);
        downloadLink.setVisible( this.includeDownloadMenu);

        // actual audio element
        WebMarkupContainer audio = new WebMarkupContainer("audio");
        audio.setOutputMarkupId(true);
        if (finalSrc != null) {
            audio.add(AttributeModifier.replace("src", finalSrc));
        } else {
            audio.add(AttributeModifier.replace("src", ""));
        }
        audio.add(AttributeModifier.replace("playbackRate", String.valueOf(this.defaultPlaybackRate))); // initial attr
        audio.add(AttributeModifier.replace("volume", String.valueOf(this.defaultVolume)));
        container.add(audio);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        // Compute markup ids from child components (ensure setOutputMarkupId(true) was called)
        String containerId = get("container").getMarkupId();
        String resetId = get("container").get("left").get("reset").getMarkupId();
        String playId = get("container").get("left").get("play").getMarkupId();
        String endId = get("container").get("left").get("end").getMarkupId();
        
        
        String timeId = get("container").get("left").get("time").getMarkupId();
        String progressId = get("container").get("center").get("progress").getMarkupId();
        String audioId = get("container").get("audio").getMarkupId();
        String volumeId = get("container").get("right").get("volume").getMarkupId();
     
        
        String speedId = get("container").get("right").get("speed").getMarkupId();
        String downloadMenuId = get("container").get("right").get("downloadMenu").getMarkupId();
        String downloadLinkId = get("container").get("right").get("downloadMenu").get("downloadLink").getMarkupId();

        // Ensure the playback rate string matches an option value (e.g. "1" not "1.0")
        String playbackRateStr;
        if (this.defaultPlaybackRate == (long) this.defaultPlaybackRate) {
            playbackRateStr = String.format("%d", (long) this.defaultPlaybackRate);
        } else {
            playbackRateStr = String.valueOf(this.defaultPlaybackRate);
        }
        String volumeStr = String.valueOf(this.defaultVolume);

        StringBuilder sb = new StringBuilder();
        sb.append("(function(){");

        sb.append("var btnReset=document.getElementById('").append(resetId).append("');");
        sb.append("var btnPlay=document.getElementById('").append(playId).append("');");
        sb.append("var btnEnd=document.getElementById('").append(endId).append("');");
        sb.append("var speed=document.getElementById('").append(speedId).append("');");
        sb.append("var timeLabel=document.getElementById('").append(timeId).append("');");
        sb.append("var progressContainer=document.getElementById('").append(progressId).append("');");
        sb.append("var progressBar=document.querySelector('#").append(progressId).append(" .wktui-progress-bar');");
        sb.append("var tooltip=document.querySelector('#").append(progressId).append(" .wktui-progress-tooltip');");
        sb.append("var audio=document.getElementById('").append(audioId).append("');");
        sb.append("var volume=document.getElementById('").append(volumeId).append("');");
        sb.append("var downloadLink=document.getElementById('").append(downloadLinkId).append("');");

        sb.append("if(!audio) return;");

        // defaults
        sb.append("try{audio.playbackRate=").append(this.defaultPlaybackRate).append(";}catch(e){};");
        sb.append("try{audio.volume=").append(this.defaultVolume).append(";}catch(e){};");
        sb.append("if(speed) speed.value='").append(playbackRateStr).append("';");
        sb.append("if(volume) volume.value='").append(volumeStr).append("';");

        // play/pause (toggle icon classes)
        sb.append("btnPlay&&btnPlay.addEventListener('click',function(){if(audio.paused)audio.play();else audio.pause();});");
        sb.append("audio.addEventListener('play',function(){ if(btnPlay){ var i=btnPlay.querySelector('i'); if(i){ i.classList.remove('fa-play'); i.classList.add('fa-stop'); } } });");
        sb.append("audio.addEventListener('pause',function(){ if(btnPlay){ var i=btnPlay.querySelector('i'); if(i){ i.classList.remove('fa-stop'); i.classList.add('fa-play'); } } });");

        // reset/end
        sb.append("btnReset&&btnReset.addEventListener('click',function(){audio.currentTime=0;updateTime();});");
        sb.append("btnEnd&&btnEnd.addEventListener('click',function(){if(audio.duration&&isFinite(audio.duration)){audio.currentTime=audio.duration;updateTime();}});");

        // speed/volume
        sb.append("speed&&speed.addEventListener('change',function(){var v=parseFloat(speed.value);if(!isNaN(v))audio.playbackRate=v;});");
        sb.append("volume&&volume.addEventListener('input',function(){audio.volume=parseFloat(volume.value);});");

        // download link
        sb.append("if(downloadLink){downloadLink.setAttribute('href',audio.src||'');downloadLink.setAttribute('download','');}");

        // progress drag + tooltip
        sb.append("(function(){");
        sb.append("if(!progressContainer) return;");
        sb.append("var dragging=false;");
        sb.append("var seekAt=function(clientX){var rect=progressContainer.getBoundingClientRect();var x=Math.max(0,Math.min(rect.width,clientX-rect.left));var pct=rect.width>0?x/rect.width:0;if(audio.duration&&isFinite(audio.duration))audio.currentTime=pct*audio.duration;if(progressBar)progressBar.style.width=(pct*100)+'%';updateTime();};");
        sb.append("progressContainer.addEventListener('mousedown',function(e){e.preventDefault();dragging=true;tooltip&&(tooltip.style.display='block');seekAt(e.clientX);});");
        sb.append("document.addEventListener('mousemove',function(e){if(dragging){e.preventDefault();seekAt(e.clientX);updateTooltip(e.clientX);}else{hoverTooltip(e.clientX);}});");
        sb.append("document.addEventListener('mouseup',function(e){if(dragging){e.preventDefault();seekAt(e.clientX);dragging=false;tooltip&&(tooltip.style.display='none');}});");
        sb.append("progressContainer.addEventListener('touchstart',function(e){if(e.touches&&e.touches[0]){e.preventDefault();dragging=true;tooltip&&(tooltip.style.display='block');seekAt(e.touches[0].clientX);}});");
        sb.append("document.addEventListener('touchmove',function(e){if(dragging&&e.touches&&e.touches[0]){e.preventDefault();seekAt(e.touches[0].clientX);updateTooltip(e.touches[0].clientX);}});");
        sb.append("document.addEventListener('touchend',function(e){if(dragging){e.preventDefault();dragging=false;tooltip&&(tooltip.style.display='none');}});");
        sb.append("var hoverTooltip=function(clientX){if(!progressContainer||!tooltip)return;var rect=progressContainer.getBoundingClientRect();if(clientX<rect.left||clientX>rect.right){tooltip.style.display='none';return;}var x=Math.max(0,Math.min(rect.width,clientX-rect.left));var pct=rect.width>0?x/rect.width:0;var dur=audio.duration||0;var t=(dur&&isFinite(dur))?Math.floor(pct*dur):0;tooltip.textContent=(Math.floor(t/60)+':'+(t%60<10?'0'+(t%60):t%60));tooltip.style.left=(x)+'px';tooltip.style.display='block';};");
        sb.append("var updateTooltip=function(clientX){if(!progressContainer||!tooltip)return;var rect=progressContainer.getBoundingClientRect();var x=Math.max(0,Math.min(rect.width,clientX-rect.left));var dur=audio.duration||0;var pct=rect.width>0?x/rect.width:0;var t=(dur&&isFinite(dur))?Math.floor(pct*dur):0;tooltip.textContent=(Math.floor(t/60)+':'+(t%60<10?'0'+(t%60):t%60));tooltip.style.left=(x)+'px';};");
        sb.append("})();");

        // time/progress updates
        sb.append(buildUpdateTimeFunction(containerId, audioId, progressId, timeId));

        sb.append("})();");

        // Render the script on DOM ready. FontAwesome is provided by the page (paid kit), so we don't load the CDN here.
        response.render(OnDomReadyHeaderItem.forScript(sb.toString()));
    }

    // helper builds a small JS function used by the script above
    private String buildUpdateTimeFunction(String containerId, String audioId, String progressId, String timeId) {
        String s = "function updateTime(){ var a=document.getElementById('" + audioId + "'); var tb=document.querySelector('#" + progressId + " .wktui-progress-bar'); var t=document.getElementById('" + timeId + "'); if(!a) return; var cur=a.currentTime||0; var dur=a.duration||0; var pct = (dur>0 && isFinite(dur))? (cur/dur)*100 : 0; if(tb) tb.style.width = pct + '%'; function fmt(x){ if(!isFinite(x)) return '0:00'; var m=Math.floor(x/60); var sec=Math.floor(x%60); return m+':'+(sec<10?'0'+sec:sec); } if(t) t.textContent = fmt(cur) + ' / ' + (isFinite(dur)? fmt(dur) : '0:00'); }";
        s += "var audioElement=document.getElementById('" + audioId + "'); if(audioElement){ audioElement.addEventListener('timeupdate', updateTime); audioElement.addEventListener('loadedmetadata', updateTime); audioElement.addEventListener('play', updateTime); audioElement.addEventListener('pause', updateTime); }";
        return s;
    }
}
