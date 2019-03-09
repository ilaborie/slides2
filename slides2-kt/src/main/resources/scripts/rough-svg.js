const defaultOptions = {
    roughness: 2,
    fillWeight: 1.5,
    stroke: 'rgba(0,0,0,.1)',
    strokeWidth: 1,
    fillStyle: 'zigzag', //cross-hatch, zigzag,
    simplification: 1
};


setTimeout(() => {
    Array.from(document.querySelectorAll('figure svg'))
        .forEach(svgElt => {
            const roughSvg = rough.svg(svgElt);
            Array.from(svgElt.querySelectorAll('path'))
                .forEach(p => {
                    const options = ['fill', 'stroke', 'strokeWidth']
                        .reduce((opt, attr) => {
                            const value = p.getAttribute(attr);
                            if (value) {
                                opt[attr] = value;
                            }
                            return opt;
                        }, defaultOptions);
                    const node = roughSvg.path(p.getAttribute('d'), options);

                    p.parentNode.replaceChild(node, p);
                });
            Array.from(svgElt.querySelectorAll('circle'))
                .forEach(p => {
                    const options = ['fill', 'stroke', 'strokeWidth']
                        .reduce((opt, attr) => {
                            const value = p.getAttribute(attr);
                            if (value) {
                                opt[attr] = value;
                            }
                            return opt;
                            //cross-hatch, zigzag,
                        }, defaultOptions);
                    const node = roughSvg.circle(
                        parseInt(p.getAttribute('cx')),
                        parseInt(p.getAttribute('cy')),
                        parseInt(p.getAttribute('r')),
                        options);

                    p.parentNode.replaceChild(node, p);
                });
        });
}, 100);
