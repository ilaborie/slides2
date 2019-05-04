(function () {

    window.CanvasSlideshow = function (options) {

        //  OPTIONS
        /// ---------------------------
        options = options || {};
        options.stageWidth = options.hasOwnProperty('stageWidth') ? options.stageWidth : 1920;
        options.stageHeight = options.hasOwnProperty('stageHeight') ? options.stageHeight : 1080;
        options.pixiSprites = options.hasOwnProperty('sprites') ? options.sprites : [];
        options.centerSprites = options.hasOwnProperty('centerSprites') ? options.centerSprites : false;
        options.autoPlay = options.hasOwnProperty('autoPlay') ? options.autoPlay : true;
        options.autoPlaySpeed = options.hasOwnProperty('autoPlaySpeed') ? options.autoPlaySpeed : [10, 3];
        options.fullScreen = options.hasOwnProperty('fullScreen') ? options.fullScreen : true;
        options.displacementImage = options.hasOwnProperty('displacementImage') ? options.displacementImage : '';
        options.displaceAutoFit = options.hasOwnProperty('displaceAutoFit') ? options.displaceAutoFit : false;
        options.wacky = options.hasOwnProperty('wacky') ? options.wacky : false;
        options.interactive = options.hasOwnProperty('interactive') ? options.interactive : false;
        options.interactionEvent = options.hasOwnProperty('interactionEvent') ? options.interactionEvent : '';
        options.displacementCenter = options.hasOwnProperty('displacementCenter') ? options.displacementCenter : false;
        options.dispatchPointerOver = options.hasOwnProperty('dispatchPointerOver') ? options.dispatchPointerOver : false;

        //  PIXI VARIABLES
        /// ---------------------------
        const renderer = new PIXI.autoDetectRenderer(options.stageWidth, options.stageHeight, {transparent: true});
        const stage = new PIXI.Container();
        const slidesContainer = new PIXI.Container();
        const displacementSprite = new PIXI.Sprite.fromImage(options.displacementImage);
        const displacementFilter = new PIXI.filters.DisplacementFilter(displacementSprite);

        /// ---------------------------
        //  INITIALISE PIXI
        /// ---------------------------
        this.initPixi = function () {

            // Add canvas to the HTML
            //document.body.appendChild( renderer.view );
            document.querySelector('.cover article').appendChild(renderer.view);

            // Add child container to the main container
            stage.addChild(slidesContainer);

            // Enable Interactions
            stage.interactive = true;

            // Fit renderer to the screen
            if (options.fullScreen === true) {
                renderer.view.style.objectFit = 'cover';
                renderer.view.style.width = '100%';
                renderer.view.style.height = '100%';
                renderer.view.style.top = '50%';
                renderer.view.style.left = '50%';
                renderer.view.style.webkitTransform = 'translate( -50%, -50% ) scale(1.1)';
                renderer.view.style.transform = 'translate( -50%, -50% ) scale(1.1)';
            } else {
                renderer.view.style.maxWidth = '100%';
                renderer.view.style.top = '50%';
                renderer.view.style.left = '50%';
                renderer.view.style.webkitTransform = 'translate( -50%, -50% )';
                renderer.view.style.transform = 'translate( -50%, -50% )';
            }

            displacementSprite.texture.baseTexture.wrapMode = PIXI.WRAP_MODES.REPEAT;

            // Set the filter to stage and set some default values for the animation
            stage.filters = [displacementFilter];

            if (options.autoPlay === false) {
                displacementFilter.scale.x = 0;
                displacementFilter.scale.y = 0;
            }

            if (options.wacky === true) {

                displacementSprite.anchor.set(0.5);
                displacementSprite.x = renderer.width / 2;
                displacementSprite.y = renderer.height / 2;
            }

            displacementSprite.scale.x = 2;
            displacementSprite.scale.y = 2;

            // PIXI tries to fit the filter bounding box to the renderer so we optionally bypass
            displacementFilter.autoFit = options.displaceAutoFit;

            stage.addChild(displacementSprite);

        };

        /// ---------------------------
        //  LOAD SLIDES TO CANVAS
        /// ---------------------------
        this.loadPixiSprites = function (sprites) {

            var rSprites = options.sprites;

            for (var i = 0; i < rSprites.length; i++) {

                var texture = new PIXI.Texture.fromImage(sprites[i]);
                var image = new PIXI.Sprite(texture);

                if (options.centerSprites === true) {
                    image.anchor.set(0.5);
                    image.x = renderer.width / 2;
                    image.y = renderer.height / 2;
                }

                slidesContainer.addChild(image);

            }

        };

        /// ---------------------------
        //  DEFAULT RENDER/ANIMATION
        /// ---------------------------
        if (options.autoPlay === true) {

            var ticker = new PIXI.ticker.Ticker();

            ticker.autoStart = options.autoPlay;

            ticker.add(function (delta) {

                displacementSprite.x += options.autoPlaySpeed[0] * delta;
                displacementSprite.y += options.autoPlaySpeed[1];
                renderer.render(stage);

            });

        } else {

            var render = new PIXI.ticker.Ticker();

            render.autoStart = true;

            render.add(function (delta) {
                renderer.render(stage);
            });

        }

        /// ---------------------------
        //  INIT FUNCTIONS
        /// ---------------------------

        this.init = () => {
            this.initPixi();
            this.loadPixiSprites(options.pixiSprites);
        };

        /// ---------------------------
        //  START
        /// ---------------------------
        this.init();

    };

})();

const local = location.protocol + '//' + location.host;

const spriteImagesSrc = [
    `${local}/cover.2e02e947.jpg`
];


// const spriteImagesSrc = [
//     'https://raw.githubusercontent.com/Pierrinho/elephant/master/bg-novius-1920.jpg'
// ];
// console.log({spriteImagesSrc});

const initCanvasSlideshow = new CanvasSlideshow({
    sprites: spriteImagesSrc,
    // displacementImage: 'https://raw.githubusercontent.com/Pierrinho/elephant/master/pattern-clouds.jpg',
    displacementImage: `${local}/clouds.adb2f3e2.jpg`,
    autoPlay: true,
    autoPlaySpeed: [0, 6],
    interactive: false,
    interactionEvent: 'click', // 'click', 'hover', 'both'
    displaceAutoFit: false,
    dispatchPointerOver: true // restarts pointerover event after click
});