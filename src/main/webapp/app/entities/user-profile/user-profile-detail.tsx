import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-profile.reducer';
import { IUserProfile } from 'app/shared/model/user-profile.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserProfileDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserProfileDetail = (props: IUserProfileDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userProfileEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="listitApp.userProfile.detail.title">UserProfile</Translate> [<b>{userProfileEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="phone">
              <Translate contentKey="listitApp.userProfile.phone">Phone</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.phone}</dd>
          <dt>
            <span id="emailEnabled">
              <Translate contentKey="listitApp.userProfile.emailEnabled">Email Enabled</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.emailEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="smsEnabled">
              <Translate contentKey="listitApp.userProfile.smsEnabled">Sms Enabled</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.smsEnabled ? 'true' : 'false'}</dd>
          <dt>
            <span id="lastLoginDate">
              <Translate contentKey="listitApp.userProfile.lastLoginDate">Last Login Date</Translate>
            </span>
          </dt>
          <dd>
            {userProfileEntity.lastLoginDate ? (
              <TextFormat value={userProfileEntity.lastLoginDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastLoginIp">
              <Translate contentKey="listitApp.userProfile.lastLoginIp">Last Login Ip</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.lastLoginIp}</dd>
          <dt>
            <span id="isLocked">
              <Translate contentKey="listitApp.userProfile.isLocked">Is Locked</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.isLocked ? 'true' : 'false'}</dd>
          <dt>
            <span id="lockDate">
              <Translate contentKey="listitApp.userProfile.lockDate">Lock Date</Translate>
            </span>
          </dt>
          <dd>
            {userProfileEntity.lockDate ? <TextFormat value={userProfileEntity.lockDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="maxImages">
              <Translate contentKey="listitApp.userProfile.maxImages">Max Images</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.maxImages}</dd>
          <dt>
            <span id="validDays">
              <Translate contentKey="listitApp.userProfile.validDays">Valid Days</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.validDays}</dd>
          <dt>
            <span id="isPrivacyEnabled">
              <Translate contentKey="listitApp.userProfile.isPrivacyEnabled">Is Privacy Enabled</Translate>
            </span>
          </dt>
          <dd>{userProfileEntity.isPrivacyEnabled ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="listitApp.userProfile.user">User</Translate>
          </dt>
          <dd>{userProfileEntity.userId ? userProfileEntity.userId : ''}</dd>
          <dt>
            <Translate contentKey="listitApp.userProfile.location">Location</Translate>
          </dt>
          <dd>{userProfileEntity.locationId ? userProfileEntity.locationId : ''}</dd>
        </dl>
        <Button tag={Link} to="/user-profile" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-profile/${userProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userProfile }: IRootState) => ({
  userProfileEntity: userProfile.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserProfileDetail);
