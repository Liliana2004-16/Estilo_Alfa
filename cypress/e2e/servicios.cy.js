describe('Controlador de Servicios', () => {
    it('debería listar todos los servicios', () => {
      cy.request('GET', 'http://localhost:8080/api/servicios')
        .then((response) => {
          expect(response.status).to.eq(200);
          expect(response.body).to.be.an('array'); 
        });
    });
  });
 
  it('debería obtener un servicio por ID', () => {
    const serviceId = 2; 
    cy.request({
        method: 'GET',
        url: `http://localhost:8080/api/servicios/${serviceId}`,
    }).then((response) => {
        expect(response.status).to.eq(200);
        expect(response.body).to.have.property('id', serviceId); 
    });
});

  
  describe('Controlador de Servicios', () => {
    it('debería crear un nuevo servicio', () => {
      const newService = {
        nombre: 'Corte de cabello',
        descripcion: 'Corte de cabello con estilo moderno',
        precio: 25.00
      };
  
      cy.request({
        method: 'POST',
        url: 'http://localhost:8080/api/servicios',
        body: newService
      }).then((response) => {
        expect(response.status).to.eq(200);  // Cambiado a 200
        expect(response.body).to.have.property('nombre', newService.nombre);
        expect(response.body).to.have.property('descripcion', newService.descripcion);
        expect(response.body).to.have.property('precio', newService.precio);
      });
    });
  });
  


  describe('Controlador de Servicios', () => {
    it('debería eliminar un servicio por ID', () => {
      const serviceId = 1;  //ID válido de un servicio en tu base de datos
      cy.request({
        method: 'DELETE',
        url: `http://localhost:8080/api/servicios/${serviceId}`,
      }).then((response) => {
        expect(response.status).to.eq(200);
      });
    });
  });
