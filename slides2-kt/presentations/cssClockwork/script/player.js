(function () {
  const parent = document.querySelector('#safetylast article');
  const message = document.querySelector("#safetylast .message");
  const videoId = 'VFBYJNAapyk';
  const playerVars = {
    autoplay: 1,
    controls: 0,
    disablekb: 1,
    start: 115,
    end: 154
  };

  const play = () => {
    const style = getComputedStyle(parent);
    const width = parseInt(style.width,10);
    const height = parseInt(style.height,10);
    new YT.Player('player', {
      videoId,
      width,
      height,
      playerVars,
      events: {onStateChange}
    });
  };

  const onStateChange = ({data}) => {
    switch (data) {
      case -1:
        console.log('buffering', {data});
        document.getElementById('player').requestFullscreen();
        break;
      case YT.PlayerState.PLAYING:
        console.log('playing', {data});
        setTimeout(() => message.classList.toggle('show'), 3 * 1000); //3s
        break;
      case YT.PlayerState.ENDED:
        console.log('ended', {data});
        document.getElementById('player').style.opacity = 0;
        if (document.fullscreenElement) {
          document.exitFullscreen();
        }
        break;
      default :
        console.log('nothing to do', {data});
    }
  };

  window.addEventListener("hashchange", () =>
    document.location.hash === '#safetylast' ? play() : '');
})();
