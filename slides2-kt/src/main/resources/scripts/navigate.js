
// Navigation
const clickOn = selector => {
    const btn = document.querySelector(selector);
    if (btn) {
        btn.click();
    }
};

const prevSelector = "section:target .previous a";
const nextSelector = "section:target .next a";

const previousSlide = () => clickOn(prevSelector);
const nextSlide = () => clickOn(nextSelector);
const home = () => clickOn("section.cover");
const nextStep = () => {
    const currentStepSlide = document.querySelector('section.steps:target');
    if (currentStepSlide) {
        const current = currentStepSlide.querySelector('.step-current');
        const next = currentStepSlide.querySelector(!current ? '.step' : '.step-current ~ .step');
        if (next) {
            if (current) {
                current.classList.toggle('step-current');
                current.classList.toggle('step-done');
            }
            next.classList.toggle('step-current');
        } else {
            // no more step, go to next slide
            clickOn(nextSelector)
        }
    } else {
        // no step, go to next slide
        clickOn(nextSelector)
    }
};

// Register handlers
const keys = {
    ArrowRight: nextSlide,
    ArrowLeft: previousSlide,
    Space: nextStep,
    Home: home
};

// listen events
document.addEventListener('keydown', event => {
    if (event.target.type !== 'textarea') {
        const {code} = event;
        if (keys[code]) {
            keys[code](event);
            event.stopPropagation()
        }
    }
});