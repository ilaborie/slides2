

{
    ArrowRight: nextSlide,
        ArrowLeft: previousSlide,
    Space: nextStep,
    Home: home
};



document.addEventListener('keydown', event => {
    if (event.target.type !== 'textarea') {
        const {code} = event;
        if (keys[code]) {
            keys[code](event);
            event.stopPropagation()
        }
    }
});