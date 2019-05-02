(() => {

    function cleanClass(element) {
        element.classList.remove('live-code');
        element.classList.remove('play');
    }

    ['section.code.live-code article', 'section.code.play article']
        .forEach(selector =>
            Array.from(document.querySelectorAll(selector))
                .forEach(elt => elt.addEventListener('click', function () {
                    cleanClass(this.parentElement);
                })));

    // Click on enter, remove blur
    document.addEventListener('keydown', event => {
        const {code} = event;
        if (code === 'Enter') {
            const currentArticle = document.querySelector('section.code:target');
            cleanClass(currentArticle);
            event.stopPropagation();
        }
    });


    // Countdown
    let time = 10 * 60; // 10 min
    const pauseSection = document.getElementById('part_pause');
    if (pauseSection) {
        const pauseArticle = pauseSection.querySelector('article');

        const renderTime = time => {
            const min = (time / 60) | 0;
            const seconds = time % 60;

            const min1 = (min / 10) | 0;
            const min2 = min % 10;
            const sec1 = (seconds / 10) | 0;
            const sec2 = seconds % 10;

            pauseArticle.innerHTML = time >= 0 ? `
            <div class="digit">${min1}</div>
            <div class="digit">${min2}</div>
            <div class="sep"></div>
            <div class="digit">${sec1}</div>
            <div class="digit">${sec2}</div>` : '';
        };

        let timeId;
        pauseSection.addEventListener('click', () => {
            if (!timeId) {
                console.debug('start Countdown');
                pauseSection.style.filter = 'none';
                renderTime(time);
                timeId = setInterval(function () {
                    time--;
                    renderTime(time);
                    if (time === 0) {
                        clearInterval(timeId);
                    }
                }, 1000);
            }
        });
    }

})();
