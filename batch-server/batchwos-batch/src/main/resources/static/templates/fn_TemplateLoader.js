function loadTemplates(instances) {
    console.info('LoadTemplates > ', instances);

    Object.keys(instances).forEach(name => {
        const instance  = instances[name];
        const element   = document.querySelector(name);
        
        element.innerHTML = instance.render();
        instance.afterRender();
    });
};