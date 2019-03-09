const defaultOptions = {
    roughness: 2,
    fillWeight: 1.5,
    stroke: 'rgba(0,0,0,.1)',
    strokeWidth: 1,
    fillStyle: 'zigzag', //cross-hatch, zigzag,
    simplification: 1
};

const buildOptions = elt => ['fill', 'stroke', 'strokeWidth']
    .reduce((opt, attr) => {
        const value = elt.getAttribute(attr);
        if (value) {
            opt[attr] = value;
        }
        return opt;
    }, defaultOptions);

setTimeout(() => {
    // only on top level svg
    const svgElt = document.querySelector('body > svg.visually-hidden');
    if (!svgElt) {
        // Nothing to do
        return;
    }
    const roughSvg = rough.svg(svgElt);

    const roughing = (selector, createNode) =>
        Array.from(svgElt.querySelectorAll(selector))
            .forEach(p => p.parentNode.replaceChild(createNode(p), p));

    roughing('path', path => roughSvg.path(path.getAttribute('d'), buildOptions(path)));
    roughing('circle', circle => {
        roughSvg.circle(
            parseInt(p.getAttribute('cx')),
            parseInt(p.getAttribute('cy')),
            parseInt(p.getAttribute('r')),
            buildOptions(circle));
    });
    // fixme handle other shape: rect, ellipse, polyline, polygon
}, 100);
