export class CertificateDetails {
  constructor(
    public issuedTo: string,
    public issuedBy: string,
    public validFrom: string,
    public version: number,
    public serialNumber: number,
    public signatureAlgorithm: string,
    public signatureHashAlgorithm: string,
    public publicKey: string,
  ) {}
}