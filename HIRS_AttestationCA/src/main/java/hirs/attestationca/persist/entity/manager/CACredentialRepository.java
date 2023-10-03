package hirs.attestationca.persist.entity.manager;

import hirs.utils.CertificateAuthorityCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CACredentialRepository extends JpaRepository<CertificateAuthorityCredential, UUID> {

    @Query(value = "SELECT * FROM Certificate where DTYPE='CertificateAuthorityCredential'", nativeQuery = true)
    @Override
    List<CertificateAuthorityCredential> findAll();
    List<CertificateAuthorityCredential> findBySubject(String subject);
    List<CertificateAuthorityCredential> findBySubjectSorted(String subject);
    CertificateAuthorityCredential findBySubjectKeyIdentifier(byte[] subjectKeyIdentifier);
}
