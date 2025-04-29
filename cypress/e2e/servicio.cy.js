describe('Servicio API Tests', () => {
  const baseUrl = 'http://localhost:8080';

  it('should list all services', () => {
    cy.request({
      method: 'GET',
      url: `${baseUrl}/api/servicios`,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.be.an('array');
    });
  });

  it('should get a service by ID', () => {
    // First create a service to get its ID
    const newService = {
      nombre: 'Test Service',
      descripcion: 'Test Description',
      precio: 100.00
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/servicios`,
      body: newService,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(200);
      const serviceId = createResponse.body.id;

      // Now get the service by ID
      cy.request({
        method: 'GET',
        url: `${baseUrl}/api/servicios/${serviceId}`,
        failOnStatusCode: false
      }).then((getResponse) => {
        expect(getResponse.status).to.eq(200);
        expect(getResponse.body).to.have.property('id', serviceId);
        expect(getResponse.body).to.have.property('nombre', newService.nombre);
      });
    });
  });

  it('should create a new service', () => {
    const newService = {
      nombre: 'New Service',
      descripcion: 'New Description',
      precio: 150.00
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/servicios`,
      body: newService,
      failOnStatusCode: false
    }).then((response) => {
      expect(response.status).to.eq(200);
      expect(response.body).to.have.property('nombre', newService.nombre);
      expect(response.body).to.have.property('descripcion', newService.descripcion);
      expect(response.body).to.have.property('precio', newService.precio);
    });
  });

  it('should delete a service', () => {
    // First create a service to delete
    const newService = {
      nombre: 'Service to Delete',
      descripcion: 'Will be deleted',
      precio: 200.00
    };

    cy.request({
      method: 'POST',
      url: `${baseUrl}/api/servicios`,
      body: newService,
      failOnStatusCode: false
    }).then((createResponse) => {
      expect(createResponse.status).to.eq(200);
      const serviceId = createResponse.body.id;

      // Now delete the service
      cy.request({
        method: 'DELETE',
        url: `${baseUrl}/api/servicios/${serviceId}`,
        failOnStatusCode: false
      }).then((deleteResponse) => {
        expect(deleteResponse.status).to.eq(200);

        // Verify the service was deleted
        cy.request({
          method: 'GET',
          url: `${baseUrl}/api/servicios/${serviceId}`,
          failOnStatusCode: false
        }).then((getResponse) => {
          expect(getResponse.status).to.eq(404);
        });
      });
    });
  });
}); 